#VERSION:43
#DS:gk-report
select batch.accounting_date, #报表日期 date hidden=1
       batch.organization_name, #我方抬头 url limit=/cdp/select-options/key/ims_and_rb_org_abbr
       replace(replace(batch.contract_with_lot, '(', '-'), ')', '') as contract_no, #合同号 hidden=1
       batch.group_name, #所属集团 hidden=1
       batch.department, #分账部门 hidden=1
       batch.product as product_code, #交易品种 hidden=1
       batch.sort, #交易品名 hidden=1
       batch.counterparty, #交易对手 hidden=1
       batch.batch_no, #货物批号
       case when pnl.contract_type in ('P', 'S') then 'ORIGINAL' when pnl.contract_type in ('STOCK-P', 'STOCK-S') then '库存' else ifnull(pnl.stat, 'COPY') end as stat, #货物类型 values limit=[{"name":"ORIGINAL","value":"ORIGINAL"},{"name":"COPY","value":"COPY"}]
       pnl.contract_no as display_contract_no, #合同号
       pnl.lot as lot_no, #LOT
       pnl.invoice_no as invoice_number, #发票号
       pnl.step, #STEP
       pnl_contract.counterparty as display_counterparty, #交易对手 url limit=/cdp/select-options/key/counterparty_name,counterparty_name_tw
       pnl_contract.direction, #交易方向 values limit=[{"name":"采购","value":"P"},{"name":"销售","value":"S"}]
       pnl.contract_currency as contract_currency, #合同币种 url limit=/cdp/select-options/key/rp_currency
       pnl.currency as invoice_currency, #发票币种 url limit=/cdp/select-options/key/rp_currency
       1 / pnl.invoice_to_contract_rate as rate, #发票汇率
       pnl.qty, #交货数量 number
       pnl.delivery_date as delivery_date, #交货日期 date
       pnl.invoice_date as invoice_date, #发票日期 date
       pnl.functional_amount + ifnull(adj.functional_amount, 0) as amount, #交货货值（本位币） number
       concat(pnl.amount_expr, ifnull(concat('<br/>', adj.functional_amount, '{尾款}'), '')) as amount_expr #交货货值expr hidden=1
from latest_acctg_rb_parcel_batch batch
    inner join latest_acctg_rb_parcel_pnl pnl on batch.batch_no = pnl.batch_no and batch.organization_name = pnl.organization_name and batch.accounting_date = pnl.accounting_date
    left join latest_acctg_rb_contract pnl_contract on pnl.contract_no = pnl_contract.contract_no and pnl.lot = pnl_contract.lot and pnl_contract.accounting_date = batch.accounting_date
    left join (
        select batch_no,
               organization_name,
               accounting_date,
               contract_type,
               invoice_no,
               parcel_step,
               sum(functional_amount) as functional_amount
        from latest_acctg_rb_parcel_pnl
        where price_type = 'Final_adjust'
        group by batch_no, organization_name, accounting_date, contract_type, invoice_no, parcel_step
    ) adj on pnl.batch_no = adj.batch_no and pnl.organization_name = adj.organization_name and pnl.accounting_date = adj.accounting_date and pnl.contract_type = adj.contract_type and pnl.invoice_no = adj.invoice_no and adj.parcel_step = pnl.parcel_step
where pnl.contract_type <> 'STOCK-MTM'
      and price_type <> 'Final_adjust'
