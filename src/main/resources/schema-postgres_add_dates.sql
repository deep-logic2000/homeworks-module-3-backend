BEGIN;

ALTER TABLE customers ADD COLUMN     created_date      TIMESTAMP  NULL;
ALTER TABLE customers ADD COLUMN     last_modified_date      TIMESTAMP  NULL;

ALTER TABLE employers ADD COLUMN     created_date      TIMESTAMP  NULL;
ALTER TABLE employers ADD COLUMN     last_modified_date      TIMESTAMP  NULL;

ALTER TABLE accounts ADD COLUMN     created_date      TIMESTAMP  NULL;
ALTER TABLE accounts ADD COLUMN     last_modified_date      TIMESTAMP  NULL;

COMMIT;