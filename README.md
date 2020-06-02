# FINFOOD

API Rest of a project for business management in the food industry, specializing in the sale of hamburguer.

### Prerequisites

The following items are necessary to run this application

```
Java
Maven
MySQL

```

## Deploy

Clone this project.
Create your MySQL repository and provide details in the application.properties file

```
spring.datasource.url= URL_MYSQL/SCHEMA_NAME
spring.datasource.username= USER_NAME_MYSQL
spring.datasource.password= PASSWORD_MYSQL

```

You can now run the application.
```
mvn spring-boot:run
```

## API
How to use the API.
In these examples I will use [Postman](https://www.getpostman.com/) to test the API.
In the 'Body' (return) tab, make sure that next to 'Preview' is selected JSON.

See all ingredients
```
1) Select the 'GET' option
2) Enter the URL: http://localhost:8080/ingredients/
3) Click on the 'Send' button
```

Add new ingredient. 

The 'ingredients' route receives 'name' which is the name of the ingredient and 'price' which is the price of the ingredient.
```
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/ingredients/
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json below. If you want to edit a ingredient, just add the 'id' before the 'name' key.
	{
		"name" : "Ingrediente",
		"price" : 0.20
	}
4) Click on the 'Send' button
```

Delete an ingredient
```
1) Select the 'DELETE' option
2) Enter the URL: http://localhost:8080/ingredients/{id}
3) Click on the 'Send' button
```

See all dishes
```
1) Select the 'GET' option
2) Enter the URL: http://localhost:8080/dishes/
3) Click on the 'Send' button
```

Add new dish with ingredients. 

Add new dish with ingredients. The 'dishes' route receives 'name' which is the dish name, 'custom' which identifies whether the dish is customized or not and 'ingredientsList' which is a list of ids of the ingredients that make up the dish.
```
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/dishes/
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json below. If you want to edit a dish, just add the 'id' before the 'name' key.
{
	"name" : "Lanche",
	"custom" : false,
	"ingredientsList" : [
		{
			"id" : 1
		},
		{
			"id" : 2
		},
		{
			"id" : 3
		}
	]
}
4) Click on the 'Send' button
```

Add new dish without ingredients. 

If 'custom' is true and 'ingredientsList' is empty, no ingredients make up the dish, as they must be chosen at checkout.
```
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/dishes/
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json below.
	{
		"name" : "Monte seu lanche",
		"custom" : true,
		"ingredientsList" : []
	}
4) Click on the 'Send' button
```

Delete a dish:
```
1) Select the 'DELETE' option
2) Enter the URL: http://localhost:8080/dishes/{id}
3) Click on the 'Send' button
```


Testing the ingredientsPromotion route. 

This route is used to create, change and delete Ligth-type promotions.
The ingredientsPromotion receives the 'name', which is the name of the promotion, the 'ingredientHave' which is the id of the ingredient that is required to apply the discount, the 'ingredientDontHave' which is the id of the ingredient that is necessary not to have to apply the discount and finally the 'discountPayment' which is the percentage of the discount.
```
CREATE AND UPDATE
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/ingredientsPromotion
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json below to create a promotion. If you want to edit a promotion, just add the 'id ' before the 'name' key.
	{
		"name" : "Promoção por ingrediente"
		"ingredientHave" : {
			"id" : 1
		},
		"ingredientDontHave" : {
			"id" : 2
		}
		"discountPayment" : 10
	}
4) Click on the 'Send' button


DELETE
1) Select the 'DELETE' option
2) Enter the URL: http://localhost:8080/ingredientsPromotion/{id}


```

Testing the quantityPromotion route. 

This route is used to create, change and delete promotions like 'Muita Carne' and 'Muito Queijo'.
The quantityPromotion receives the 'name', which is the name of the promotion, the 'ingredient' which is the id of the ingredient that has the promotion, the 'ingredientQuantity' which is the amount of the ingredient needed to apply the discount and finally ' ingredientPayment 'which is the amount of the ingredient that will be charged.
```
CREATE AND UPDATE
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/quantityPromotion
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json below to create a promotion. If you want to edit a promotion, just add the 'id' before the 'name' key.
	{
		"name" : "Promocao por quantidade",
		"ingredient" : {
			"id" : 1
		},
		"ingredientQuantity" : 3,
		"ingredientPayment" : 2
	}
4) Click on the 'Send' button


DELETE
1) Select the 'DELETE' option
2) Enter the URL: http://localhost:8080/quantityPromotion/{id}


```


Testing the 'checkout' route. 

The checkout receives 'dishId' which is the id of the chosen dish and 'ingredientsIds' which is a list of the ids of the optional (extra) ingredients chosen.
```
1) Select the 'POST' option
2) Enter the URL: http://localhost:8080/checkout
3) Click on the 'Body' tab (send), choose the radio 'raw' and paste the json:
	{
		"dishId" : 6,
		"ingredientsIds" : [1,2,3]
	}
	 or
	{
		"dishId" : 6,
		"ingredientsIds" : []
	}
4) Click on the 'Send' button

```

## Unit Tests

To execute the tests you run the following command

```
mvn clean test

```


## Build with

* [Java](https://www.java.com/pt_BR/)
* [MySQL](https://www.mysql.com/)

## Author

* **Vinicius Fontão** -  [ViniciusFontao](https://github.com/viniciusfontao)