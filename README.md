# MAPZIP

## 기능

- 식당
  - 식당 조회
    - 카테고리 표현.
  - 식당 등록
  - 식당 제거
  - 식당 카테고리 등록
- 카테고리
  - 카테고리로 식당 조회.
  -
- 유튜버
- 회원

## ClassDiagram

```mermaid
classDiagram
    Member ..|> Restaurant
    Member ..|> Youtuber
    Restaurant "1".."*" RestaurantYoutuber
    RestaurantYoutuber "*".."1" Youtuber
    Restaurant "1".."*" RestaurantCategory
    RestaurantCategory "*".."1" Category

    class Member{
        -Long id
        -String name
        -String password
        -List<Restaurant> favoriteRestaurants
        -List<Youtuber> favoriteYoutubers
    }

    class Restaurant{
        -Long id
        -String name
        -Address address
        -Long count
        -String description
        -List<RestaurantYoutuber> restarantYoutubers
        -List<RestaurantCategory> restaurantCategorys
    }

    class Address{
        <<Value Type>>
        -String street
        -double latitude
        -double longitude
        +void setAddress(String street)
        +void setAddress(double latitude, double longitude)
    }

    class Youtuber{
        -Long id
        -String name
        -String url
        -List<RestaurantYoutuber> restarantYoutubers
    }

    class RestaurantYoutuber{
        -Long id
        -Restaurant restaurant
        -Youtuber youtuber
    }

    class Category{
        -Long id
        -String name
        -List<RestaurantCategory> restaurantCategorys
    }

    class RestaurantCategory{
        -Long id
        -Restaurant restaurant
        -Category category
    }
```

## ER Diagram

```mermaid
erDiagram
    Member ||--o{ Restaurant:like
    Member ||--o{ Youtuber:like
    Restaurant ||..o{ RestaurantYoutuber:recommend
    RestaurantYoutuber }o..|| Youtuber:recommend
    Restaurant ||..o{ RestaurantCategory:is
    RestaurantCategory }o--|| Category:is
```

## 식당 저장

- 식당 주소는 도로명으로 변환되어 저장.
- 식당 주소로 부터 좌표 값을 얻어와 저장.
