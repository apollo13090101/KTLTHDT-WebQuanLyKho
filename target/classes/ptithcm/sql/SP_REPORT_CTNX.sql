ALTER PROC [dbo].[SP_REPORT_CTNX]
@NGAYBD DATE,
@NGAYKT DATE,
@TYPE CHAR(6)  -- IMPORT/EXPORT
AS
BEGIN
	IF @TYPE = 'IMPORT'
	BEGIN
		SELECT FORMAT(PN.created, 'MM/yyyy') AS 'THANGNAM', 
		VT.name as 'TENVT', 
		SUM(CT.quantity) AS 'TONGSOLUONG', 
		SUM(CT.quantity * CT.price) AS 'TONGTRIGIA'
		FROM
		(
			SELECT code, created
			FROM dbo.receipt
			WHERE created BETWEEN @NGAYBD AND @NGAYKT AND active = 1
		) PN
		INNER JOIN dbo.receipt_detail CT ON CT.receipt_code = PN.code and CT.active = 1
		INNER JOIN dbo.product VT ON VT.code = CT.product_code and VT.active = 1
		GROUP BY FORMAT(PN.created, 'MM/yyyy'), VT.name
		ORDER BY 1
	END
	ELSE IF @TYPE = 'EXPORT'
	BEGIN
		SELECT FORMAT(PX.created, 'MM/yyyy') AS 'THANGNAM', 
		VT.name as 'TENVT', 
		SUM(CT.quantity) AS 'TONGSOLUONG', 
		SUM(CT.quantity * CT.price) AS 'TONGTRIGIA'
		FROM
		(
			SELECT code, created
			FROM dbo.issue
			WHERE created BETWEEN @NGAYBD AND @NGAYKT AND active = 1
		) PX
		INNER JOIN dbo.issue_detail CT ON CT.issue_code = PX.code and CT.active = 1
		INNER JOIN dbo.product VT ON VT.code = CT.product_code and VT.active = 1
		GROUP BY FORMAT(PX.created, 'MM/yyyy'), VT.name
		ORDER BY 1
	END
END

/*
 EXEC SP_REPORT_CTNX '2016-01-01', '2022-01-01', 'IMPORT'
 EXEC SP_REPORT_CTNX '2016-01-01', '2022-01-01', 'EXPORT'
*/