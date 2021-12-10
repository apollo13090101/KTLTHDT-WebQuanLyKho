ALTER proc [dbo].[SP_CHECK_ORDER_DETAIL]
@ORD_CODE varchar(10), 
@PROD_CODE varchar(10)
as
begin
	if exists (select od.order_code, od.product_code from order_detail as od where od.order_code = @ORD_CODE and od.product_code = @PROD_CODE)
		return 1;
	else
		return 0;
end

go

/*

DECLARE @ret int 
EXEC @ret = SP_CHECK_ORDER_DETAIL 'ORD0002', 'PROD0001'
SELECT 'Return Value' = @ret

*/