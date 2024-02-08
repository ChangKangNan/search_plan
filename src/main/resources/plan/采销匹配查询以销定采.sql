#VERSION:133
#DS:gk-report
#CHANGE_TABLE:accounting_date point=LAST_DAY date_rule=yyMM error_tip=找不到该报表日期的数据
select accounting_date, #报表日期 date limit_search_conditions=[{"name":"等于","value":"Equal"}]
       batch_no, #货物批号
       s_contract_no, #销售合同号
       p_contract_no, #采购合同号
       s_counterparty, #销售交易对手 url limit=/cdp/select-options/key/counterparty_name,counterparty_name_tw
       p_counterparty, #采购交易对手 url limit=/cdp/select-options/key/counterparty_name,counterparty_name_tw
       s_stock_type, #销售交货方式
       p_stock_type, #采购交货方式
       s_business_date, #销售交货日期 date
       p_business_date, #采购交货日期 date
       s_invoice_month, #销售发票月份 month
       p_invoice_month,  #采购发票月份 month
       brand, #交易品牌 url limit=/cdp/select-options/key/rb_brand,ims_brand
       p_warehouse, #采购仓库 url limit=/cdp/select-options/key/ims_warehouse,rb_warehouse
       s_warehouse, #销售仓库 url limit=/cdp/select-options/key/ims_warehouse,rb_warehouse
       s_card_no, #销售卡号
       p_card_no, #采购卡号
       type, #货物类型
       organization_name, #我方抬头 url limit=/cdp/select-options/key/ims_and_rb_org_abbr
       fh_no, #货流编号
       product, #交易品种 url limit=/cdp/select-options/key/rp_spot_product_name
       sort, #交易品名 url limit=/cdp/select-options/key/ims_product_sort_name
       group_name, #所属集团 url limit=/cdp/select-options/key/ims_and_rb_org_group
       department, #分账部门 hidden=1
       qty, #采销数量 number
       purchase_amount, #采购货值(￥) number
       purchase_amount_expr, #采购货值expr hidden=1
       purchase_price, #采购单价 number
       sale_amount, #销售货值(￥) number
       sale_amount_expr, #销售货值expr hidden=1
       sale_price, #销售单价 number
       pnl, #采销匹配PNL(￥) number
       functional_purchase_amount, #采购货值(本位币) number
       functional_purchase_amount_expr, #采购货值本位币expr hidden=1
       functional_sale_amount, #销售货值(本位币) number
       functional_sale_amount_expr, #销售货值本位币expr hidden=1
       functional_pnl, #采销匹配PNL(本位币) number
       business_date, #交货日期 date
       ps_close_account, #采销关账 values limit=[{"name":"已关账","value":"1"},{"name":"-","value":"0"}]
       ims #ims hidden=1
from (
    select ps.accounting_date as accounting_date,                    
           ps.batch_no,      

           any_value(s_parcel.contract_no) as s_contract_no,       
           group_concat(distinct p_parcel.contract_no) as p_contract_no,       
           any_value(s_parcel.counterparty) as s_counterparty,                                                            
           group_concat(distinct p_parcel.counterparty) as p_counterparty,                                                            
           any_value(s_parcel.stock_type) as s_stock_type,        
           group_concat(distinct p_parcel.stock_type) as p_stock_type,        
           any_value(s_parcel.business_date) as s_business_date,             
           group_concat(distinct p_parcel.business_date) as p_business_date,             
           any_value(s_parcel.invoice_month) as s_invoice_month,              
           group_concat(distinct p_parcel.invoice_month) as p_invoice_month,              
           if(find_in_set(any_value(s_parcel.brand), group_concat(distinct p_parcel.brand)), group_concat(distinct p_parcel.brand), concat(any_value(s_parcel.brand), group_concat(distinct p_parcel.brand)))   as brand,    
           any_value(s_parcel.warehouse) as s_warehouse,      
           group_concat(distinct p_parcel.warehouse) as p_warehouse,      
           any_value(s_parcel.card_no) as s_card_no,      
           group_concat(distinct p_parcel.card_no) as p_card_no,      

           null as type,
           any_value(batch.organization_name) as organization_name,                                                            
           ps.fh_no,      
           any_value(batch.product) as product,                                                             
           any_value(batch.sort) as sort,                                                              
           any_value(batch.group_name) as group_name,                                                             
           any_value(batch.department) as department,               

           sum(if(ps.price_type in ('基准价', '来源价', '点价', '市场均价', '基准价预测', '点完,提>点', '确价,提>点') and ps.direction = 'P', ps.qty, 0)) as qty,             

           sum(if(ps.direction = 'P', round(ps.amount_cny, 2), 0)) as purchase_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps.direction = 'P', concat(price_type, ':\t', ifnull(amount_cny_expr, log)), null)                ), '<br/>'),                '</pre>'            ) as purchase_amount_expr,                   
           round(sum(if(ps.direction = 'P', round(ps.amount_cny, 2), 0)) / sum(if(ps.price_type in ('基准价', '来源价', '点价', '市场均价', '基准价预测', '点完,提>点', '确价,提>点') and ps.direction = 'P', ps.qty, 0)), 2) as purchase_price,             
           sum(if(ps.direction = 'S', round(ps.amount_cny, 2), 0)) as sale_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps.direction = 'S', concat(price_type, ':\t', ifnull(amount_cny_expr, log)), null)                ), '<br/>'),                '</pre>'            ) as sale_amount_expr,                   
           round(sum(if(ps.direction = 'S', round(ps.amount_cny, 2), 0)) / sum(if(ps.price_type in ('基准价', '来源价', '点价', '市场均价', '基准价预测', '点完,提>点', '确价,提>点') and ps.direction = 'P', ps.qty, 0)), 2) as sale_price,             
           sum(-1 * ps.amount_cny) as pnl,                   

           sum(if(ps.direction = 'P', round(ps.amount_cny, 2), 0)) as functional_purchase_amount,
           null as functional_purchase_amount_expr,
           sum(if(ps.direction = 'S', round(ps.amount_cny, 2), 0)) as functional_sale_amount,
           null as functional_sale_amount_expr,
           sum(-1 * ps.amount_cny) as functional_pnl,

           max(if(ps.direction = 'P', 0, ps.business_date)) as business_date,           
           any_value(contract.ps_close_account) as ps_close_account,                                                                         
           true as ims
    from latest_acctg_parcel_pnl/*replace to history_acctg_m@this_parcel_pnl*/ ps
        left join latest_acctg_parcel_batch/*replace to history_acctg_m@this_parcel_batch*/ batch on ps.batch_no = batch.batch_no and ps.accounting_date = batch.accounting_date
        left join latest_acctg_spot_contract/*replace to history_acctg_m@this_spot_contract*/ contract on ps.contract_no = contract.contract_no and ps.accounting_date = contract.accounting_date and (!ifnull(contract.opening_data, false)) 
        left join latest_acctg_parcel/*replace to history_acctg_m@this_parcel*/ s_parcel on ps.fh_no = s_parcel.no and s_parcel.direction = 'S' and ps.accounting_date = s_parcel.accounting_date
        left join latest_acctg_parcel/*replace to history_acctg_m@this_parcel*/ p_parcel on ps.direction = 'P' and ps.no = p_parcel.no and p_parcel.direction = 'P' and ps.accounting_date = p_parcel.accounting_date
    where ps.type = '采销'
    group by ps.fh_no, ps.batch_no, ps.accounting_date
    union all
    select ps.accounting_date as accounting_date,                    
           ps.batch_no,      

           group_concat(distinct if(ps.contract_type = 'S', concat(ps.contract_no, '-', ps.lot), null)) as s_contract_no,       
           group_concat(distinct if(ps.contract_type = 'P', concat(ps.contract_no, '-', ps.lot), null)) as p_contract_no,       
           group_concat(distinct if(ps.contract_type = 'S', ps_contract.counterparty, null)) as s_counterparty,                                                            
           group_concat(distinct if(ps.contract_type = 'P', ps_contract.counterparty, null)) as p_counterparty,                                                            
           null as s_stock_type,        
           null as p_stock_type,        
           group_concat(distinct if(ps.contract_type = 'S', ps.delivery_date, null)) as s_business_date,             
           group_concat(distinct if(ps.contract_type = 'P', ps.delivery_date, null)) as p_business_date,             
           group_concat(distinct if(ps.contract_type = 'S', cast(ps.invoice_date / 100 as unsigned), null)) as s_invoice_month,              
           group_concat(distinct if(ps.contract_type = 'P', cast(ps.invoice_date / 100 as unsigned), null)) as p_invoice_month,              
           group_concat(distinct ps.brand) as brand,      
           group_concat(distinct if(ps.contract_type = 'S', ps.warehouse, null)) as s_warehouse,      
           group_concat(distinct if(ps.contract_type = 'P', ps.warehouse, null)) as p_warehouse,      
           group_concat(distinct if(ps.contract_type = 'S', ps.doc_number, null)) as s_card_no,      
           group_concat(distinct if(ps.contract_type = 'P', ps.doc_number, null)) as p_card_no,      

           (                case                    when contract_type in ('P', 'S') then '1&99'                    when contract_type = 'MIDDLE' then '2-98'                    when contract_type = 'MIDDLE-ORIGINAL' then 'ORIGINAL'                    when contract_type = 'MIDDLE-COPY' then 'MIDDLE-COPY'                end            ) as type,      
           ps.organization_name,                                                            
           null as fh_no,
           group_concat(distinct batch.product) as product_code,                                                             
           group_concat(distinct ps_contract.commodity) as sort,                                                              
           any_value(batch.group_name) as group_name,                                                             
           any_value(ifnull(batch.department, '')) as department,               

           if(any_value(contract_type) in ('P', 'S'), any_value(batch.ps_qty), null) as qty,             

           sum(if(ps_contract.direction = 'P', ps.cny_amount, 0)) as purchase_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps_contract.direction = 'P', concat(if(ps.contract_type like 'MIDDLE', 'MIDDLE\t', ''), ps.contract_no, '(LOT:', ps.lot, ',INV:', ifnull(ps.original_invoice_no, ps.invoice_no), '):\t', ifnull(concat(amount_expr, ' * ', contract_to_cny_rate, '{合同转CNY汇率}'), ps.log)), null)                ), '<br/>'),                '</pre>'            ) as purchase_amount_expr,                   
           round(sum(if(ps_contract.direction = 'P', ps.cny_amount, 0)) / if(any_value(contract_type) in ('P', 'S'), any_value(batch.ps_qty), null), 2) as purchase_price,             
           sum(if(ps_contract.direction = 'S', ps.cny_amount, 0)) as sale_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps_contract.direction = 'S', concat(if(ps.contract_type like 'MIDDLE', 'MIDDLE\t', ''), ps.contract_no, '(LOT:', ps.lot, ',INV:', ifnull(ps.original_invoice_no, ps.invoice_no), '):\t', ifnull(concat(amount_expr, ' * ', contract_to_cny_rate, '{合同转CNY汇率}'), ps.log)), null)                ), '<br/>'),                '</pre>'            ) as sale_amount_expr,                   
           round(sum(if(ps_contract.direction = 'S', ps.cny_amount, 0)) / if(any_value(contract_type) in ('P', 'S'), any_value(batch.ps_qty), null), 2) as sale_price,             
           sum(-1 * ps.cny_amount) as pnl,                   

           sum(if(ps_contract.direction = 'P', ps.functional_amount, 0)) as functional_purchase_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps_contract.direction = 'P', concat(if(ps.contract_type like 'MIDDLE', 'MIDDLE\t', ''), ps.contract_no, '(LOT:', ps.lot, ',INV:', ifnull(ps.original_invoice_no, ps.invoice_no), '):\t', ifnull(concat(amount_expr, ' * ', contract_to_functional_rate, '{合同转本位币汇率}'), ps.log)), null)                ), '<br/>'),                '</pre>'            ) as functional_purchase_amount_expr,                    
           sum(if(ps_contract.direction = 'S', ps.functional_amount, 0)) as functional_sale_amount,                
           concat(                '<pre style="max-height:50vh;overflow-y:auto;">',                json_join(json_arrayagg(                    if(ps_contract.direction = 'S', concat(if(ps.contract_type like 'MIDDLE', 'MIDDLE\t', ''), ps.contract_no, '(LOT:', ps.lot, ',INV:', ifnull(ps.original_invoice_no, ps.invoice_no), '):\t', ifnull(concat(amount_expr, ' * ', contract_to_functional_rate, '{合同转本位币汇率}'), ps.log)), null)                ), '<br/>'),                '</pre>'            ) as functional_sale_amount_expr,                    
           sum(-1 * ps.functional_amount) as functional_pnl,                   

           max(if(ps_contract.direction = 'P', 0, ps.delivery_date)) as business_date,           
           any_value(ps_contract.ps_close_account) as ps_close_account,                                                                         
           false as ims
    from latest_acctg_rb_parcel_pnl/*replace to history_acctg_m@this_rb_parcel_pnl*/ ps
        left join latest_acctg_rb_parcel_batch/*replace to history_acctg_m@this_rb_parcel_batch*/ batch on ps.batch_no = batch.batch_no and ps.organization_name = batch.organization_name and ps.accounting_date = batch.accounting_date
        left join latest_acctg_rb_contract/*replace to history_acctg_m@this_rb_contract*/ ps_contract on ps.contract_no = ps_contract.contract_no and ps.lot = ps_contract.lot and ps.accounting_date = ps_contract.accounting_date
    where ps.contract_type in ('P', 'S', 'MIDDLE', 'MIDDLE-ORIGINAL', 'MIDDLE-COPY')
    group by ps.batch_no, ps.organization_name, type, ps.accounting_date
) all_ps
where true
