# Secure Online Auction System
Online auction however is a different business model where the items are sold through price bidding.
Usually bidding have start price and ending time. Potential buyers in auction and the winner is the one
who bids the item for highest price. We treat the fraud detection with a binary classification. For buying
product online user have to provide his personal details like email address, license number,etc. Only the
valid user will have authority to bid. This prevents various frauds according in online shopping.
## Modules
### Customer Module:
~~- Customer register: customer will be provided with a personal account through registration~~
~~- Customer must provide email address, license number. Email cannot be duplicated,
password in DB is encrypted.~~
~~- Customer Login: Login to the system with valid username and password.~~
~~- View auction product:~~
- Customer can view products page with [pagination or infinite loop (Optional)]
~~- Customer can view product detail page~~
~~- Search Product by name~~
~~- Auction Products: Only verified customer can able to view auction to bidding for product.~~
~~- Customer must make a deposit before bidding, the deposit is 10% of the starting price by
default. As a seller, it can set a deposit amount.~~
- View bid history including all bidding prices, bidding item, etc. bidding history is organized
yearly.
### Seller Module
The seller module includes different sellers who wish to sell their products. A seller can add or delete or
modify information about different items but only their items. The different functionalities for seller are
**~~- Register as a seller: the same as “Customer” Module.~~**
~~- Can add a new a product, a product can be in several categories, description, bid starting price,
deposit, bid due date, bidding payment due date.~~
~~- When add a new product, seller can choose “save without release”, or “save and release”.~~
~~- If choose save without release, seller can update all information about the product and
delete the product. And customer cannot see the product.~~
~~- If choose save and release, customer can see the product and bid. And seller are no longer
available to update the product.~~
### Bidding System
- Only verified customer can bid product, seller cannot bid their own products.
- Customer can only increase the price, not decrease
- Always display the current highest bid,
- Display how many customer bid on the product (Optional)
- After the bid due date, close the bidding system
- Pick the highest bidder as the winner
- Return deposit to all bidders except the winner
- Customer must pay full amount of the bidding price in the certain days which is configured by the
seller.
- If not paid, charge deposit only