ALTER proc [dbo].[SP_CHECK_ISSUE_DETAIL]
@ISS_CODE varchar(10), 
@PROD_CODE varchar(10)
as
begin
	if exists (select id.issue_code, id.product_code from issue_detail as id where id.issue_code = @ISS_CODE and id.product_code = @PROD_CODE)
		return 1;
	else
		return 0;
end

go

/*

DECLARE @ret int 
EXEC @ret = SP_CHECK_ISSUE_DETAIL 'EXP0001', 'PROD0001'
SELECT 'Return Value' = @ret

*/