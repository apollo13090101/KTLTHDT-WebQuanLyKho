insert into menu (parent_id, url, name, order_index) values 
(0, '/product', 'Product', 1),
(0, '/stock', 'Stock', 2),
(0, '/report', 'Report', 3),
(0, '/manage', 'Manage', 4),

 -- Parent ID = 1
(1, '/category/list', 'List of Categories', 1),
(1, '/category/view', 'View', -1),
(1, '/category/edit', 'Edit', -1),
(1, '/category/add', 'Add', -1),
(1, '/category/save', 'Save', -1),
(1, '/category/delete', 'Delete', -1),

(1, '/product/list', 'List of Products', 2),
(1, '/product/view', 'View', -1),
(1, '/product/edit', 'Edit', -1),
(1, '/product/add', 'Add', -1),
(1, '/product/save', 'Save', -1),
(1, '/product/delete', 'Delete', -1),

 -- Parent ID = 2
(2, '/order/list', 'Orders', 1),
(2, '/order/view', 'View', -1),
(2, '/order/edit', 'Edit', -1),
(2, '/order/add', 'Add', -1),
(2, '/order/save', 'Save', -1),
(2, '/order/delete', 'Delete', -1),


(2, '/order-detail/list', 'Orders Detail', 2),
(2, '/order-detail/view', 'View', -1),
(2, '/order-detail/edit', 'Edit', -1),
(2, '/order-detail/add', 'Add', -1),
(2, '/order-detail/save', 'Save', -1),
(2, '/order-detail/delete', 'Delete', -1),


(2, '/receipt/list', 'Receipts', 3),
(2, '/receipt/view', 'View', -1),
(2, '/receipt/edit', 'Edit', -1),
(2, '/receipt/add', 'Add', -1),
(2, '/receipt/save', 'Save', -1),
(2, '/receipt/delete', 'Delete', -1),


(2, '/receipt-detail/list', 'Receipts Detail', 4),
(2, '/receipt-detail/view', 'View', -1),
(2, '/receipt-detail/edit', 'Edit', -1),
(2, '/receipt-detail/add', 'Add', -1),
(2, '/receipt-detail/save', 'Save', -1),
(2, '/receipt-detail/delete', 'Delete', -1),


(2, '/issue/list', 'Issues', 5),
(2, '/issue/view', 'View', -1),
(2, '/issue/edit', 'Edit', -1),
(2, '/issue/add', 'Add', -1),
(2, '/issue/save', 'Save', -1),
(2, '/issue/delete', 'Delete', -1),


(2, '/issue-detail/list', 'Issues Detail', 6),
(2, '/issue-detail/view', 'View', -1),
(2, '/issue-detail/edit', 'Edit', -1),
(2, '/issue-detail/add', 'Add', -1),
(2, '/issue-detail/save', 'Save', -1),
(2, '/issue-detail/delete', 'Delete', -1),


(2, '/product-in-stock/list', 'Products in Stock', 7),
(2, '/history/list', 'History', 8),

 -- Parent ID = 3
(3, '/ctnx', 'Detail of Import/Export', 1),
(3, '/hdnv', 'Evaluation of Employee', 2),
(3, '/ddhkpn', 'Orders without Receipts', 3),
(3, '/thnx', 'Summary of Import/Export', 4),

(3, '/ctnx/export', 'Export PDF', -1),
(3, '/hdnv/export', 'Export PDF', -1),
(3, '/ddhkpn/export', 'Export PDF', -1),
(3, '/thnx/export', 'Export PDF', -1),

 -- Parent ID = 4
(4, '/user/list', 'List of Users', 1),
(4, '/role/list', 'List of Roles', 2),
(4, '/menu/list', 'List of Menus', 3),

(4, '/user/save', 'Save', -1),
(4, '/user/edit', 'Edit', -1),
(4, '/user/view', 'View', -1),
(4, '/user/add', 'Add', -1),
(4, '/user/delete', 'Delete', -1),

(4, '/role/save', 'Save', -1),
(4, '/role/edit', 'Edit', -1),
(4, '/role/view', 'View', -1),
(4, '/role/add', 'Add', -1),
(4, '/role/delete', 'Delete', -1),

(4, '/menu/permission', 'Permission', -1),
(4, '/menu/update-permission', 'Update Permission', -1),
(4, '/menu/change-status', 'Change Status', -1)

