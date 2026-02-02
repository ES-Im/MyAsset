insert into users (
    user_key, status,
    role, created_at,
    last_login_at, withdraw_req_at
) values (
     'default', 'ACTIVE',
     'USER', '2000-01-01 00:00:00',
     '2000-01-01 00:00:00', null
 );

insert into user_info(
    user_key, provider_id,
    username, email,
    provider_type
) values (
    'default', 'default'
    , 'default', 'default@myasset.temp'
    , 'GOOGLE'
);


insert into category (
    user_key, cgy_name,
    is_active, expense_type, created_at
) values
      ('default', '주거관리비', true, 'FIXED', '2020-01-01 00:00:00'),
      ('default', '보험', true, 'FIXED', '2020-01-01 00:00:00'),
      ('default', '구독비', true, 'FIXED', '2020-01-01 00:00:00'),
      ('default', '교통비', true, 'FIXED', '2020-01-01 00:00:00'),
      ('default', '식비', true, 'VARIABLE', '2020-01-01 00:00:00'),
      ('default', '생활비', true, 'VARIABLE', '2020-01-01 00:00:00'),
      ('default', '여가기타', true, 'VARIABLE', '2020-01-01 00:00:00'),
      ('default', '적금', true, 'INSTALLMENT_SAVINGS', '2020-01-01 00:00:00');

insert into notification_rule (
    user_key, noti_type, created_at
) values
      ('default', 'BUDGET_EXCEEDED', '2020-01-01 00:00:00'),
      ('default', 'DAILY_SPEND_SUMMARY', '2020-01-01 00:00:00'),
      ('default', 'WEEKLY_SPEND_SUMMARY', '2020-01-01 00:00:00'),
      ('default', 'MONTHLY_SPEND_SUMMARY', '2020-01-01 00:00:00'),
      ('default', 'CARD_PAYMENT_DUE', '2020-01-01 00:00:00'),
      ('default', 'STOCK_PRICE_SURGE', '2020-01-01 00:00:00'),
      ('default', 'STOCK_PRICE_DROP', '2020-01-01 00:00:00'),
      ('default', 'ABNORMAL_REPEATED_TRANSACTION', '2020-01-01 00:00:00'),
      ('default', 'ABNORMAL_EXPENSE', '2020-01-01 00:00:00');
