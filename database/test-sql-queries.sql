select * from product;

SELECT product_id, product_sku, name, description, price, image_name
FROM product
WHERE name ILIKE '%g%' 
OR product_sku ILIKE '%A%';

INSERT into product (product_sku, name, description, price, image_name)
VALUES (?, ?, ?, ?, ?)
RETURNING product_id;

select * from product;

delete from cart_item
where product_id = 2;

delete from product
where product_id = 2;

-- "UPDATE bookmark SET title=?, url=?, description=?, is_public=?, is_flagged=? WHERE bookmark_id=?;"

UPDATE product SET product_sku=?, name=?, description=?, price=?, image_name=?
WHERE product_id=?;

select * from cart_item;

select cart_item_id, user_id, product_id, quantity
from cart_item
where cart_item_id = 1;

INSERT INTO cart_item (user_id, product_id, quantity)
VALUES (2, 2, 2)
RETURNING cart_item_id;

UPDATE cart_item SET user_id=1, product_id=3, quantity=3
WHERE cart_item_id = 3;