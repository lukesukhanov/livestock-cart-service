-- changeset lukesukhanov:1

SET search_path TO livestock_shop_dev;

-- 'product_in_cart' table
DROP TABLE IF EXISTS product_in_cart CASCADE;
CREATE TABLE product_in_cart (
	id bigint NOT NULL PRIMARY KEY DEFAULT nextval('common_id_seq'),
	users_id bigint NOT NULL REFERENCES users(id),
	product_id bigint NOT NULL REFERENCES product(id),
	quantity integer NOT NULL CHECK (quantity > 0),
	created_at timestamp with time zone NOT NULL,
	last_modified_at timestamp with time zone NOT NULL
);
-- rollback DROP TABLE IF EXISTS product_in_cart CASCADE;

-- Trigger for inserting the 'created_at' column
DROP TRIGGER IF EXISTS sync_insert_created_at_column ON product_in_cart;
CREATE TRIGGER sync_insert_created_at_column
	BEFORE INSERT
	ON product_in_cart
	FOR EACH ROW 
	EXECUTE FUNCTION sync_insert_created_at_column();
-- rollback DROP TRIGGER IF EXISTS sync_insert_created_at_column ON product_in_cart;

-- Trigger for updating the 'created_at' column
DROP TRIGGER IF EXISTS sync_update_created_at_column ON product_in_cart;
CREATE TRIGGER sync_update_created_at_column
	BEFORE UPDATE
	ON product_in_cart
	FOR EACH ROW 
	EXECUTE FUNCTION sync_update_created_at_column();
-- rollback DROP TRIGGER IF EXISTS sync_update_created_at_column ON product_in_cart;

-- Trigger for inserting the 'last_modified_at' column
DROP TRIGGER IF EXISTS sync_insert_last_modified_at_column ON product_in_cart;
CREATE TRIGGER sync_insert_last_modified_at_column
	BEFORE INSERT
	ON product_in_cart
	FOR EACH ROW 
	EXECUTE FUNCTION sync_last_modified_at_column();
-- rollback DROP TRIGGER IF EXISTS sync_insert_last_modified_at_column ON product_in_cart;

-- Trigger for updating the 'last_modified_at' column
DROP TRIGGER IF EXISTS sync_update_last_modified_at_column ON product_in_cart;
CREATE TRIGGER sync_update_last_modified_at_column
	BEFORE UPDATE
	ON product_in_cart
	FOR EACH ROW 
	EXECUTE FUNCTION sync_last_modified_at_column();
-- rollback DROP TRIGGER IF EXISTS sync_update_last_modified_at_column ON product_in_cart;