
CREATE TABLE IF NOT EXISTS "VENDINGMACHINEDB"."audit" (
  "id" int(11) NOT NULL,
  "TotalSales" float NOT NULL,
   PRIMARY KEY ("id")
);


CREATE TABLE IF NOT EXISTS "VENDINGMACHINEDB"."config" (
  "id" int(11) NOT NULL,
  "Username" varchar2(200) NOT NULL,
  "Password" varchar2(200) NOT NULL,
  "Rows" int(11) NOT NULL,
  "Columns" int(11) NOT NULL,
  "UnitPrice" varchar(10) NOT NULL,
   PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "VENDINGMACHINEDB"."products" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "ProductName" varchar2(200) NOT NULL,
  "Quantity" int(11) NOT NULL,
  "Price" float NOT NULL,
  PRIMARY KEY ("id")
);
