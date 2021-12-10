ALTER proc [dbo].[SP_REPORT_HDNV]
@MANV INT,			--Mã nhân viên
@NGAYBD DATE,		--Từ ngày
@NGAYKT DATE,		--Đến ngày
@TYPE CHAR(6)	    --Phiếu Nhập or Phiếu Xuất (NHAP-XUAT)
AS
BEGIN
	IF(@TYPE = 'IMPORT')
	BEGIN
		SELECT
		FORMAT(PN.created, 'MMMM yyyy') AS THANGNAM,         --Để Group theo tháng/năm
		PN.created AS NGAY,
		CTPN.receipt_code AS MAPHIEU,                        --Đồng bộ cột khi SP chứa 2 câu query 2 cột MAPN,PAPX khác nhau
		N'Không có' AS HOTENKH,	                             --Vì ở Phiếu Nhập ko có KH nên thêm cột giả như ở PX
		TENVT = (SELECT VT.name FROM dbo.product AS VT		 --Từ MAVT lọc ra được tên TENVT
				WHERE VT.code = CTPN.product_code and VT.active = 1),
		CTPN.quantity as SOLUONG,
		CTPN.price as DONGIA,
		THANHTIEN = quantity * price
		FROM dbo.receipt_detail AS CTPN
		INNER JOIN 
			(SELECT *		--Lọc các PN thỏa MANV và thời gian trước
			 FROM dbo.receipt
			 WHERE user_id = @MANV 
			 AND (created BETWEEN @NGAYBD AND @NGAYKT) and active = 1) AS PN
		ON PN.code = CTPN.receipt_code and CTPN.active = 1
	END
	ELSE IF(@TYPE = 'EXPORT')
	BEGIN
		SELECT
		FORMAT(PX.created,'MMMM yyyy') AS THANGNAM, 
		PX.created as NGAY,
		CTPX.issue_code AS MAPHIEU,
		PX.customer AS HOTENKH,
		TENVT  = (SELECT VT.name FROM dbo.product AS VT
				  WHERE VT.code = CTPX.product_code and VT.active = 1),
		CTPX.quantity as SOLUONG,
		CTPX.price as DONGIA,
		THANHTIEN = quantity * price
		FROM dbo.issue_detail AS CTPX
		INNER JOIN
			(SELECT *		--Lọc các PN thỏa MANV và thời gian trước
			FROM dbo.issue
			WHERE user_id = @MANV 
			AND (created BETWEEN @NGAYBD AND @NGAYKT) and active = 1) AS PX
		ON PX.code = CTPX.issue_code and CTPX.active = 1
	END
END

/*
 EXEC SP_REPORT_HDNV 1, '2016-01-01', '2022-01-01', 'IMPORT'
 EXEC SP_REPORT_HDNV 1, '2016-01-01', '2022-01-01', 'EXPORT'
*/