-- changeset lukesukhanov:3

SET search_path TO livestock_stage;

-- Trigger function for synchronizing 'created_at' column during INSERT
CREATE OR REPLACE FUNCTION sync_insert_created_at_column() RETURNS trigger AS
$$
BEGIN
	NEW.created_at := now();
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;
-- rollback DROP FUNCTION IF EXISTS sync_insert_created_at_column CASCADE;

-- Trigger function for synchronizing 'created_at' column during UPDATE
CREATE OR REPLACE FUNCTION sync_update_created_at_column() RETURNS trigger AS
$$
BEGIN
	NEW.created_at := OLD.created_at;
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;
-- rollback DROP FUNCTION IF EXISTS sync_update_created_at_column CASCADE;

-- Trigger function for synchronizing 'last_modified_at' column during INSERT and UPDATE
CREATE OR REPLACE FUNCTION sync_last_modified_at_column() RETURNS trigger AS
$$
BEGIN
	NEW.last_modified_at := now();
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;
-- rollback DROP FUNCTION IF EXISTS sync_last_modified_at_column CASCADE;