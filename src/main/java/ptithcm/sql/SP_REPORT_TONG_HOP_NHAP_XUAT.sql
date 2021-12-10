ALTER PROC [dbo].[SP_REPORT_TONG_HOP_NHAP_XUAT]
	@NGAYBD date, @NGAYKT date
AS
BEGIN
	SET NOCOUNT ON;
		IF 1=0 BEGIN
			SET FMTONLY OFF
		END
		SELECT	format(PN.created, 'yyyy-MM-dd') as NGAYNHAP,
				NHAP = SUM(CTPN.quantity * CTPN.price), 
				TYLENHAP = (SUM(CTPN.quantity * CTPN.price) / (SELECT SUM(quantity*price) FROM dbo.receipt_detail INNER JOIN dbo.receipt ON dbo.receipt_detail.receipt_code=dbo.receipt.code WHERE format(dbo.receipt.created, 'yyyy-MM-dd') BETWEEN @NGAYBD AND @NGAYKT and dbo.receipt.active = 1)) INTO #PhieuNhapTemp
		FROM dbo.receipt AS PN
		INNER JOIN (SELECT * FROM dbo.receipt_detail where active = 1) AS CTPN 
		ON PN.code = CTPN.receipt_code
		WHERE format(PN.created, 'yyyy-MM-dd') BETWEEN @NGAYBD AND @NGAYKT and PN.active = 1
		GROUP BY format(PN.created, 'yyyy-MM-dd')

		SELECT	format(PX.created, 'yyyy-MM-dd') as NGAYXUAT,
				XUAT = SUM(CTPX.quantity * CTPX.price),
				TYLEXUAT = (SUM(CTPX.quantity * CTPX.price) / (SELECT SUM(quantity*price) FROM dbo.issue_detail INNER JOIN dbo.issue ON dbo.issue_detail.issue_code=dbo.issue.code WHERE format(dbo.issue.created, 'yyyy-MM-dd') BETWEEN @NGAYBD AND @NGAYKT and dbo.issue.active = 1))  INTO #PhieuXuatTemp
		FROM dbo.issue AS PX
		INNER JOIN (SELECT * FROM dbo.issue_detail where active = 1) AS CTPX 
		ON PX.code = CTPX.issue_code
		WHERE format(PX.created, 'yyyy-MM-dd') BETWEEN @NGAYBD AND @NGAYKT and PX.active = 1
		GROUP BY format(PX.created, 'yyyy-MM-dd')			

		SELECT 
		ISNULL(NGAYNHAP, NGAYXUAT) AS NGAY, 
		ISNULL(PN.NHAP, 0) AS NHAP,
		ISNULL(PN.TYLENHAP, 0) AS TYLENHAP,
		ISNULL(PX.XUAT, 0) AS XUAT,
		ISNULL(PX.TYLEXUAT, 0) AS TYLEXUAT
		FROM #PhieuNhapTemp AS PN
		FULL JOIN #PhieuXuatTemp AS PX
		ON NGAYNHAP = NGAYXUAT
		ORDER BY NGAY
END

-- EXEC SP_REPORT_TONG_HOP_NHAP_XUAT '2017-01-01', '2022-09-01'