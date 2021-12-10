ALTER PROC [dbo].[SP_REPORT_DDH_KHONG_PN]
AS 
BEGIN
	SELECT DH.code as 'MasoDDH', DH.created as 'NGAY', DH.supplier as 'NhaCC',
	TENNV = (SELECT CONCAT(last_name, ' ', first_name) FROM dbo.users WHERE id = DH.user_id and active = 1), 
	TENVT = (SELECT VT.name FROM dbo.product AS VT WHERE VT.code = CT.product_code and VT.active = 1), 
	CT.quantity as 'SOLUONG', 
	CT.price as 'DONGIA' 
	FROM dbo.order_detail AS CT
	INNER JOIN(
		SELECT code, created, supplier, user_id FROM dbo.orders
		WHERE code NOT IN ( SELECT order_code FROM dbo.receipt) and active = 1
	) AS DH
	ON DH.code = CT.order_code and CT.active = 1
END

-- EXEC SP_REPORT_DDH_KHONG_PN