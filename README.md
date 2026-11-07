# DripVora Fashion E-Commerce Backend

Spring Boot 4.1.1 / Java 21 backend for a fashion store.

## Main modules

- Authentication + email OTP + JWT HttpOnly cookie
- User profile
- Product browsing and multipart Cloudinary image upload
- Categories
- Cart
- Wishlist
- Checkout and stock reduction
- Orders and tracking
- Payment service layer
- Reviews
- Admin dashboard
- Admin users/products/categories/orders
- Redis-ready configuration
- Resend email integration
- MySQL + JPA

## Run

1. Start MySQL database `dripora`.
2. Start Redis on port `6379`.
3. Check `src/main/resources/application.properties`.
4. Run on Windows:

```text
mvnw.cmd spring-boot:run
```

or:

```text
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

## Important

Cloudinary and Resend values in `application.properties` must be valid for your accounts.

For Resend testing mode, the recipient generally needs to be the allowed testing recipient until a sending domain is verified.

## Important endpoints

### Auth

```text
POST /dripora/signup
POST /dripora/login
POST /dripora/verify-otp
POST /dripora/resend-otp
```

### Store

```text
GET /dripora/products
GET /dripora/products/{id}
GET /dripora/categories
GET /dripora/categories/{id}
```

### User

```text
GET /dripora/user/profile
PUT /dripora/user/profile
DELETE /dripora/user/profile
```

### Cart

```text
GET /dripora/cart
POST /dripora/cart/items
PUT /dripora/cart/items/{productId}?quantity=2
DELETE /dripora/cart/items/{productId}
DELETE /dripora/cart
```

### Orders

```text
POST /dripora/orders/checkout
GET /dripora/orders
GET /dripora/orders/{id}
GET /dripora/orders/{id}/tracking
```

### Payment

```text
POST /dripora/payment
```

### Wishlist

```text
GET /dripora/wishlist
POST /dripora/wishlist/{productId}
DELETE /dripora/wishlist/{productId}
```

### Admin

```text
GET    /dripora/admin/dashboard
GET    /dripora/admin/users
PUT    /dripora/admin/users/{id}/active?value=true
POST   /dripora/admin/products
PUT    /dripora/admin/products/{id}
DELETE /dripora/admin/products/{id}
POST   /dripora/admin/categories
PUT    /dripora/admin/categories/{id}
DELETE /dripora/admin/categories/{id}
GET    /dripora/admin/orders
PUT    /dripora/admin/orders/{id}/status?status=SHIPPED
```

Product admin create/update uses `multipart/form-data` with:

- `product`: JSON object
- `image`: image file

JWT is written to the `access_token` HttpOnly cookie after successful OTP verification.
