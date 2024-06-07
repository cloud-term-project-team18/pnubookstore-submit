package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.core.s3.S3Uploader;
import org.example.pnubookstore.domain.order.entity.Order;
import org.example.pnubookstore.domain.order.repository.OrderJpaRepository;
import org.example.pnubookstore.domain.product.dto.*;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.entity.Location;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.repository.*;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepositoryForProduct userJpaRepositoryForProduct;
    private final SubjectJpaRepository subjectJpaRepository;
    private final ProductPictureJpaRepository productPictureJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;
    private final SubjectCustomRepositoryImpl subjectCustomRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Transactional
    // 물품 등록
    public void createProduct(CreateProductDto createProductDto, User user) throws IOException {
        // 유저 존재 여부 체크(추후 변경될 수 있음)
//        User findedSeller = userJpaRepositoryForProduct.findById(1L).orElseThrow();
        User findedSeller = findUser(user);

        // 과목 존재 여부 체크
        Subject findedSubject = findSubject(createProductDto);

        // 과목이 존재하지 않을 시 과목 저장)
        if (findedSubject == null){
            findedSubject = saveSubject(createProductDto);
        }

        Location createdLocation = saveLocation(createProductDto);

        // 물품 저장
        Product createdProduct = saveProduct(createProductDto, findedSeller, findedSubject, createdLocation);
        createdLocation.setProduct(createdProduct);

        // 물품 사진 저장(추후 변경 예정)
        saveImage(createProductDto.getProductPicture(), createdProduct);
    }

    // 물품 조회
    public FindProductDto findProduct(Long productId){
        Product findedProduct = productJpaRepository.findByIdFetchJoin(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        ProductPicture productPicture = productPictureJpaRepository.findByProduct(findedProduct)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()));


        return FindProductDto.of(findedProduct, productPicture.getUrl());
//        return FindProductDto.of(findedProduct, "http://example.com");
    }

    // 물품 리스트 조회
    // 물품명, 가격, 저자, 물품 이미지
    public Page<FindProductsDto> findProductList(int page, String college, String department, String professor, String subjectName){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Product> productList = null;

        if(college == "" && department == "" && professor == "" && subjectName == ""){
            productList = productJpaRepository.findAll(pageable);
        }
        else{
            List<Subject> subjects = subjectCustomRepository.findSubjects(
                    college, department, professor, subjectName);
            productList = productJpaRepository.findBySubjectIn(pageable, subjects);
        }

        List<FindProductsDto> findProductsDtoList = new ArrayList<>();

        for (Product product : productList){
//            ProductPicture productPicture = productPictureJpaRepository.findFirstByProduct(product)
//                    .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()));
//
//            String pictureUrl = productPicture.getUrl();

            findProductsDtoList.add(
                    FindProductsDto.builder()
                            .productId(product.getId())
                            .productName(product.getProductName())
                            .price(product.getPrice())
                            .seller(product.getSeller().getNickname())
                            .build()
            );
        }

        return new PageImpl<>(findProductsDtoList, pageable, productList.getTotalElements());
    }

    @Transactional
    public void updateProduct(Long productId, CreateProductDto updateProductDto) throws IOException {
//        findUser(updateProductDto);

        Product findedProduct = productJpaRepository.findById(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        Subject foundSubject = findSubject(updateProductDto);


        Location findedLocation = findedProduct.getLocation();
        findedLocation.updateLocation(updateProductDto);


        findedProduct.updateProduct(updateProductDto, foundSubject);

        // 이미지 변경
//        productPictureJpaRepository.deleteAllByProduct(findedProduct);s
//        saveImages(updateProductDto.getProductPictureList(), findedProduct);
    }

    @Transactional
    public void deleteProduct(Long productId){
        Product findedProduct = productJpaRepository.findById(productId)
                        .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));
        productPictureJpaRepository.deleteAllByProduct(findedProduct);
        productJpaRepository.delete(findedProduct);
    }

    public List<BuyProductDto> findBuyProducts(int page, User user){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

//        User findBuyer = userJpaRepositoryForProduct.findById(1L).orElseThrow();
        User findBuyer = findUser(user);
        Page<Order> orders = orderJpaRepository.findOrderByBuyer(findBuyer, pageable);

        List<BuyProductDto> buyProductDtos = new ArrayList<>();
        for(Order order : orders){
            buyProductDtos.add(
                    BuyProductDto.builder()
                            .productId(order.getProduct().getId())
                            .price(order.getMoney())
                            .productName(order.getProduct().getProductName())
                            .seller(order.getSeller().getNickname())
                            .build()
            );
        }

        return buyProductDtos;
    }

    public List<SaleProductDto> findSaleProducts(int page, User user){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<Product> findSaleProducts = productJpaRepository.findBySeller(pageable, user);

        List<SaleProductDto> saleProductDtos = new ArrayList<>();
        for(Product product : findSaleProducts){
            saleProductDtos.add(SaleProductDto.builder()
                    .productId(product.getId())
                    .productName(product.getProductName())
                    .saleStatus(product.getSaleStatus())
                    .price(product.getPrice())
                    .build());
        }

        return saleProductDtos;
    }

    public FindUserDto findUserInfo(User user){
        User findUser = findUser(user);
        return FindUserDto.builder()
                .nickname(findUser.getNickname())
                .email(findUser.getEmail())
                .build();
    }

    public FindProductDto findBuyProduct(Long productId, User buyer){
        Product foundBuyProduct = productJpaRepository.findByIdFetchJoin(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        Order foundOrder = orderJpaRepository.findOrderByBuyerAndProduct(buyer, foundBuyProduct)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.ORDER_NOT_FOUND.getErrorMessage()));

        ProductPicture productPicture = productPictureJpaRepository.findByProduct(foundBuyProduct)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()));


        return FindProductDto.of(foundBuyProduct, productPicture.getUrl());
//        return FindProductDto.of(foundBuyProduct, "http://example.com");
    }

    private User findUser(User user){
        return userJpaRepositoryForProduct.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.USER_NOT_FOUND.getErrorMessage()));
    }

    private Subject findSubject(CreateProductDto createProductDto){
        return subjectJpaRepository.findBySubjectNameAndCollegeAndDepartmentAndProfessor(
            createProductDto.getSubjectName(), createProductDto.getCollege(), createProductDto.getDepartment(), createProductDto.getProfessor());
    }

    private Subject saveSubject(CreateProductDto createProductDto){
        return subjectJpaRepository.save(
                Subject.builder()
                        .subjectName(createProductDto.getSubjectName())
                        .professor(createProductDto.getProfessor())
                        .department(createProductDto.getDepartment())
                        .build());
    }

    private Product saveProduct(CreateProductDto createProductDto, User seller, Subject subject, Location location){
        return productJpaRepository.save(
                Product.builder()
                        .seller(seller)
                        .subject(subject)
                        .location(location)
                        .productName(createProductDto.getProductName())
                        .price(createProductDto.getPrice())
                        .description(createProductDto.getDescription())
                        .author(createProductDto.getAuthor())
                        .pubDate(LocalDateTime.now())
                        .saleStatus(SaleStatus.NOT_YET)
                        .underline(createProductDto.getUnderline())
                        .note(createProductDto.getNote())
                        .naming(createProductDto.getNaming())
                        .discolor(createProductDto.getDiscolor())
                        .damage(createProductDto.getDamage())
                        .build());
    }

    private void saveImage(MultipartFile imageFile, Product product) throws IOException {
            String imageUrl = s3Uploader.upload(imageFile, "images");
            productPictureJpaRepository.save(
                    ProductPicture.builder()
                            .url(imageUrl)
                            .product(product)
                            .build());
    }

    private Location saveLocation(CreateProductDto createProductDto){
        return locationJpaRepository.save(
                Location.builder()
                        .buildingName(createProductDto.getBuildingName())
                        .lockerNumber(createProductDto.getLockerNumber())
                        .password(createProductDto.getPassword())
                        .build()
        );
    }
}
