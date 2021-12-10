/*
use master
go
USE tempdb;
GO
DECLARE @SQL nvarchar(1000);
IF EXISTS (SELECT 1 FROM sys.databases WHERE [name] = N'INT14103')
BEGIN
    SET @SQL = N'USE [INT14103];

                 ALTER DATABASE INT14103 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
                 USE [tempdb];

                 DROP DATABASE INT14103;';
    EXEC (@SQL);
END;

go
create database INT14103
go
*/
use INT14103
go
drop table receipt_detail
go
drop table issue_detail
go
drop table order_detail
go
drop table issue
go
drop table receipt
go
drop table orders
go
drop table history
go
drop table stock
go
drop table product
go
drop table category
go
drop table auth
go
drop table user_role
go
drop table menu
go
drop table role
go
drop table users
go
use INT14103
go
create table users
(
	id int identity(1, 1) not null,
	last_name nvarchar(50) not null,
	first_name nvarchar(20) not null,
	username varchar(50) not null,
	password varchar(50) not null,
	email varchar(100),
	address nvarchar(100),
	birthday date,
	salary decimal(15, 2) not null,
	--image_url varchar(1000),
	active int,
	created datetime,
	updated datetime,

	constraint PK_users primary key (id),
	constraint UK_users_username unique (username)
)
go
alter table users add constraint DF_users_salary default (4000000) for salary
go
alter table users add constraint DF_users_active default (1) for active
go
alter table users add constraint DF_users_created default (getdate()) for created
go
alter table users add constraint DF_users_updated default (getdate()) for updated
go
create table role
(
	id int identity(1, 1) not null,
	role_name nvarchar(20) not null,
	description nvarchar(100),
	active int,
	created datetime,
	updated datetime,

	constraint PK_role primary key (id),
	constraint UK_role_rolename unique (role_name)
)
go
alter table role add constraint DF_role_active default (1) for active
go
alter table role add constraint DF_role_created default (getdate()) for created
go
alter table role add constraint DF_role_updated default (getdate()) for updated
go
create table user_role
(
	id int identity(1, 1) not null,
	user_id int not null,
	role_id int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_userrole_id primary key (id),
	constraint FK_userrole_users foreign key (user_id) references users (id) on update cascade,
	constraint FK_userrole_role foreign key (role_id) references role (id) on update cascade
)
go
alter table user_role add constraint DF_userrole_active default (1) for active
go
alter table user_role add constraint DF_userrole_created default (getdate()) for created
go
alter table user_role add constraint DF_userrole_updated default (getdate()) for updated
go
create table menu
(
	id int identity(1, 1) not null,
	parent_id int not null,
	url varchar(1000) not null,
	name nvarchar(100) not null,
	order_index int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_menu primary key (id)
)
go
alter table menu add constraint DF_menu_orderindex default (0) for order_index
go
alter table menu add constraint DF_menu_active default (1) for active
go
alter table menu add constraint DF_menu_created default (getdate()) for created
go
alter table menu add constraint DF_menu_updated default (getdate()) for updated
go
create table auth
(
	id int identity(1, 1) not null,
	role_id int not null,
	menu_id int not null,
	permission int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_auth primary key (id),
	constraint FK_auth_role foreign key (role_id) references role (id) on update cascade,
	constraint FK_auth_menu foreign key (menu_id) references menu (id) on update cascade
)
go
alter table auth add constraint DF_auth_permission default (1) for permission
go
alter table auth add constraint DF_auth_active default (1) for active
go
alter table auth add constraint DF_auth_created default (getdate()) for created
go
alter table auth add constraint DF_auth_updated default (getdate()) for updated
go
create table category
(
	code varchar(10) not null,
	name nvarchar(50) not null,
	description nvarchar(100),
	active int,
	created datetime,
	updated datetime,

	constraint PK_category primary key (code)
)
go
alter table category add constraint DF_category_active default (1) for active
go
alter table category add constraint DF_category_created default (getdate()) for created
go
alter table category add constraint DF_category_updated default (getdate()) for updated
go
insert into category (code, name, description) 
values ('CATE0001', N'Thiết bị điện tử & Gia dụng', N'Electronics')
go
insert into category (code, name, description) 
values ('CATE0002', N'Smartphones & Laptops', N'Smartphones')
go
insert into category (code, name, description) 
values ('CATE0003', N'Thời trang & Mỹ phẩm', N'Fashion')
go
insert into category (code, name, description) 
values ('CATE0004', N'Sách & Tạp chí', N'Books')
go
create table product
(
	code varchar(10) not null,
	name nvarchar(100) not null,
	image_url varchar(1000) not null,
	unit nvarchar(10) not null,
	category_code varchar(10) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_product primary key (code),
	constraint FK_product_category foreign key (category_code) references category (code) on update cascade,
)
go
alter table product add constraint DF_product_active default (1) for active
go
alter table product add constraint DF_product_created default (getdate()) for created
go
alter table product add constraint DF_product_updated default (getdate()) for updated
go
create table stock
(
	id int identity(1, 1) not null,
	product_code varchar(10) not null,
	quantity int not null,
	--price decimal(15, 2) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_stock primary key (id),
	constraint FK_stock_product foreign key (product_code) references product (code) on update cascade,
)
go
alter table stock add constraint CK_stock_quantity check (quantity >= 0)
go
--alter table stock add constraint CK_stock_price check (price >= 0)
go
alter table stock add constraint DF_stock_active default (1) for active
go
alter table stock add constraint DF_stock_created default (getdate()) for created
go
alter table stock add constraint DF_stock_updated default (getdate()) for updated
go
create table history
(
	id int identity(1, 1) not null,
	product_code varchar(10) not null,
	action nvarchar(50) not null,
	type int not null,
	quantity int not null,
	price decimal(15, 2) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_history primary key (id),
	constraint FK_history_product foreign key (product_code) references product (code) on update cascade,
)
go
alter table history add constraint DF_history_active default (1) for active
go
alter table history add constraint DF_history_created default (getdate()) for created
go
alter table history add constraint DF_history_updated default (getdate()) for updated
go
create table orders
(
	code varchar(10) not null,
	supplier nvarchar(100) not null,
	user_id int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_orders primary key (code),
	constraint FK_orders_users foreign key (user_id) references users (id) on update cascade
)
go
alter table orders add constraint DF_orders_active default (1) for active
go
alter table orders add constraint DF_orders_created default (getdate()) for created
go
alter table orders add constraint DF_orders_updated default (getdate()) for updated
go
create table order_detail
(
	id int identity(1, 1) not null,
	order_code varchar(10) not null,
	product_code varchar(10) not null,
	quantity int not null,
	price decimal(15, 2) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_orderdetail primary key (id),
	constraint UK_orderdetail unique (order_code, product_code),
	constraint FK_orderdetail_orders foreign key (order_code) references orders (code) on update cascade,
	constraint FK_orderdetail_product foreign key (product_code) references product (code) on update cascade
)
go
alter table order_detail add constraint CK_orderdetail_quantity check (quantity >= 0)
go
alter table order_detail add constraint CK_orderdetail_price check (price >= 0)
go
alter table order_detail add constraint DF_orderdetail_active default (1) for active
go
alter table order_detail add constraint DF_orderdetail_created default (getdate()) for created
go
alter table order_detail add constraint DF_orderdetail_updated default (getdate()) for updated
go
create table receipt
(
	code varchar(10) not null,
	order_code varchar(10) not null,
	user_id int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_receipt primary key (code),
	constraint UK_receipt unique (order_code),
	constraint FK_receipt_orders foreign key (order_code) references orders (code) on update cascade,
	constraint FK_receipt_users foreign key (user_id) references users (id)
)
go
alter table receipt add constraint DF_receipt_active default (1) for active
go
alter table receipt add constraint DF_receipt_created default (getdate()) for created
go
alter table receipt add constraint DF_receipt_updated default (getdate()) for updated
go
create table receipt_detail
(
	id int identity(1, 1) not null,
	receipt_code varchar(10) not null,
	product_code varchar(10) not null,
	quantity int not null,
	price decimal(15, 2) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_receiptdetail primary key (id),
	constraint UK_receiptdetail unique (receipt_code, product_code),
	constraint FK_receiptdetail_receipt foreign key (receipt_code) references receipt (code) on update cascade,
	constraint FK_receiptdetail_product foreign key (product_code) references product (code) on update cascade
)
go
alter table receipt_detail add constraint CK_receiptdetail_quantity check (quantity >= 0)
go
alter table receipt_detail add constraint CK_receiptdetail_price check (price >= 0)
go
alter table receipt_detail add constraint DF_receiptdetail_active default (1) for active
go
alter table receipt_detail add constraint DF_receiptdetail_created default (getdate()) for created
go
alter table receipt_detail add constraint DF_receiptdetail_updated default (getdate()) for updated
go
create table issue
(
	code varchar(10) not null,
	customer nvarchar(100) not null,
	user_id int not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_issue primary key (code),
	constraint FK_issue_users foreign key (user_id) references users (id) on update cascade
)
go
alter table issue add constraint DF_issue_active default (1) for active
go
alter table issue add constraint DF_issue_created default (getdate()) for created
go
alter table issue add constraint DF_issue_updated default (getdate()) for updated
go
create table issue_detail
(
	id int identity(1, 1) not null,
	issue_code varchar(10) not null,
	product_code varchar(10) not null,
	quantity int not null,
	price decimal(15, 2) not null,
	active int,
	created datetime,
	updated datetime,

	constraint PK_issuedetail primary key (id),
	constraint UK_issuedetail unique (issue_code, product_code),
	constraint FK_issuedetail_issue foreign key (issue_code) references issue (code) on update cascade,
	constraint FK_issuedetail_product foreign key (product_code) references product (code) on update cascade,
)
go
alter table issue_detail add constraint CK_issuedetail_quantity check (quantity >= 0)
go
alter table issue_detail add constraint CK_issuedetail_price check (price >= 0)
go
alter table issue_detail add constraint DF_issuedetail_active default (1) for active
go
alter table issue_detail add constraint DF_issuedetail_created default (getdate()) for created
go
alter table issue_detail add constraint DF_issuedetail_updated default (getdate()) for updated
go