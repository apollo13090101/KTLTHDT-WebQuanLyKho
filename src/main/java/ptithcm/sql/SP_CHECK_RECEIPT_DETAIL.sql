ALTER proc [dbo].[SP_CHECK_RECEIPT_DETAIL]
@REC_CODE varchar(10), 
@PROD_CODE varchar(10)
as
begin
	if exists (select rd.receipt_code, rd.product_code from receipt_detail as rd where rd.receipt_code = @REC_CODE and rd.product_code = @PROD_CODE)
		return 1;
	else
		return 0;
end



go

/*

DECLARE @ret int 
EXEC @ret = SP_CHECK_RECEIPT_DETAIL 'IMP0001', 'PROD0001'
SELECT 'Return Value' = @ret

*/