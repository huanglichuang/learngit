REPLACE INTO logs_dwd
SELECT ip,(CASE WHEN date LIKE '%Jan%' THEN CONCAT(SUBSTR(REPLACE(date,'Jan','01'),7,4),'-',
       SUBSTR(REPLACE(date,'Jan','01'),4,2),'-',SUBSTR(REPLACE(date,'Jan','01'),1,2),' ',
       SUBSTR(REPLACE(date,'Jan','01'),12,8))
WHEN date LIKE '%Feb%' THEN CONCAT(SUBSTR(REPLACE(date,'Feb','02'),7,4),'-',
       SUBSTR(REPLACE(date,'Feb','02'),4,2),'-',SUBSTR(REPLACE(date,'Feb','02'),1,2),' ',
       SUBSTR(REPLACE(date,'Feb','02'),12,8))
WHEN date LIKE '%Mar%' THEN CONCAT(SUBSTR(REPLACE(date,'Mar','03'),7,4),'-',
       SUBSTR(REPLACE(date,'Mar','03'),4,2),'-',SUBSTR(REPLACE(date,'Mar','03'),1,2),' ',
       SUBSTR(REPLACE(date,'Mar','03'),12,8))
WHEN date LIKE '%Apr%' THEN CONCAT(SUBSTR(REPLACE(date,'Apr','04'),7,4),'-',
       SUBSTR(REPLACE(date,'Apr','04'),4,2),'-',SUBSTR(REPLACE(date,'Apr','04'),1,2),' ',
       SUBSTR(REPLACE(date,'Apr','04'),12,8))
WHEN date LIKE '%May%' THEN CONCAT(SUBSTR(REPLACE(date,'May','05'),7,4),'-',
       SUBSTR(REPLACE(date,'May','05'),4,2),'-',SUBSTR(REPLACE(date,'May','05'),1,2),' ',
       SUBSTR(REPLACE(date,'May','05'),12,8))
WHEN date LIKE '%Jun%' THEN CONCAT(SUBSTR(REPLACE(date,'Jun','06'),7,4),'-',
       SUBSTR(REPLACE(date,'Jun','06'),4,2),'-',SUBSTR(REPLACE(date,'Jun','06'),1,2),' ',
       SUBSTR(REPLACE(date,'Jun','06'),12,8))
WHEN date LIKE '%Jul%' THEN CONCAT(SUBSTR(REPLACE(date,'Jul','07'),7,4),'-',
       SUBSTR(REPLACE(date,'Jul','07'),4,2),'-',SUBSTR(REPLACE(date,'Jul','07'),1,2),' ',
       SUBSTR(REPLACE(date,'Jul','07'),12,8))
WHEN date LIKE '%Aug%' THEN CONCAT(SUBSTR(REPLACE(date,'Aug','08'),7,4),'-',
       SUBSTR(REPLACE(date,'Aug','08'),4,2),'-',SUBSTR(REPLACE(date,'Aug','08'),1,2),' ',
       SUBSTR(REPLACE(date,'Aug','08'),12,8))
WHEN date LIKE '%Sep%' THEN CONCAT(SUBSTR(REPLACE(date,'Sep','09'),7,4),'-',
       SUBSTR(REPLACE(date,'Sep','09'),4,2),'-',SUBSTR(REPLACE(date,'Sep','09'),1,2),' ',
       SUBSTR(REPLACE(date,'Sep','09'),12,8))
WHEN date LIKE '%Oct%' THEN CONCAT(SUBSTR(REPLACE(date,'Oct','10'),7,4),'-',
       SUBSTR(REPLACE(date,'Oct','10'),4,2),'-',SUBSTR(REPLACE(date,'Oct','10'),1,2),' ',
       SUBSTR(REPLACE(date,'Oct','10'),12,8))
WHEN date LIKE '%Nov%' THEN CONCAT(SUBSTR(REPLACE(date,'Nov','11'),7,4),'-',
       SUBSTR(REPLACE(date,'Nov','11'),4,2),'-',SUBSTR(REPLACE(date,'Nov','11'),1,2),' ',
       SUBSTR(REPLACE(date,'Nov','11'),12,8))
WHEN date LIKE '%Dec%' THEN CONCAT(SUBSTR(REPLACE(date,'Dec','12'),7,4),'-',
       SUBSTR(REPLACE(date,'Dec','12'),4,2),'-',SUBSTR(REPLACE(date,'Dec','12'),1,2),' ',
       SUBSTR(REPLACE(date,'Dec','12'),12,8))
 END),user_id,phone_sysyem,phone_type,network,(CASE WHEN date LIKE '%Jan%' THEN CONCAT(SUBSTR(REPLACE(date,'Jan','01'),7,4),
       SUBSTR(REPLACE(date,'Jan','01'),4,2),SUBSTR(REPLACE(date,'Jan','01'),1,2))
WHEN date LIKE '%Feb%' THEN CONCAT(SUBSTR(REPLACE(date,'Feb','02'),7,4),
       SUBSTR(REPLACE(date,'Feb','02'),4,2),SUBSTR(REPLACE(date,'Feb','02'),1,2))
WHEN date LIKE '%Mar%' THEN CONCAT(SUBSTR(REPLACE(date,'Mar','03'),7,4),
       SUBSTR(REPLACE(date,'Mar','03'),4,2),SUBSTR(REPLACE(date,'Mar','03'),1,2))
WHEN date LIKE '%Apr%' THEN CONCAT(SUBSTR(REPLACE(date,'Apr','04'),7,4),
       SUBSTR(REPLACE(date,'Apr','04'),4,2),SUBSTR(REPLACE(date,'Apr','04'),1,2))
WHEN date LIKE '%May%' THEN CONCAT(SUBSTR(REPLACE(date,'May','05'),7,4),
       SUBSTR(REPLACE(date,'May','05'),4,2),SUBSTR(REPLACE(date,'May','05'),1,2))
WHEN date LIKE '%Jun%' THEN CONCAT(SUBSTR(REPLACE(date,'Jun','06'),7,4),
       SUBSTR(REPLACE(date,'Jun','06'),4,2),SUBSTR(REPLACE(date,'Jun','06'),1,2))
WHEN date LIKE '%Jul%' THEN CONCAT(SUBSTR(REPLACE(date,'Jul','07'),7,4),
       SUBSTR(REPLACE(date,'Jul','07'),4,2),SUBSTR(REPLACE(date,'Jul','07'),1,2))
WHEN date LIKE '%Aug%' THEN CONCAT(SUBSTR(REPLACE(date,'Aug','08'),7,4),
       SUBSTR(REPLACE(date,'Aug','08'),4,2),SUBSTR(REPLACE(date,'Aug','08'),1,2))
WHEN date LIKE '%Sep%' THEN CONCAT(SUBSTR(REPLACE(date,'Sep','09'),7,4),
       SUBSTR(REPLACE(date,'Sep','09'),4,2),SUBSTR(REPLACE(date,'Sep','09'),1,2))
WHEN date LIKE '%Oct%' THEN CONCAT(SUBSTR(REPLACE(date,'Oct','10'),7,4),
       SUBSTR(REPLACE(date,'Oct','10'),4,2),SUBSTR(REPLACE(date,'Oct','10'),1,2))
WHEN date LIKE '%Nov%' THEN CONCAT(SUBSTR(REPLACE(date,'Nov','11'),7,4),
       SUBSTR(REPLACE(date,'Nov','11'),4,2),SUBSTR(REPLACE(date,'Nov','11'),1,2))
WHEN date LIKE '%Dec%' THEN CONCAT(SUBSTR(REPLACE(date,'Dec','12'),7,4),
       SUBSTR(REPLACE(date,'Dec','12'),4,2),SUBSTR(REPLACE(date,'Dec','12'),1,2))
 END),phone_browser,wechat_version
FROM logs_data
WHERE date_add(curdate(), interval -1 day)=DATE((CASE WHEN date LIKE '%Jan%' THEN CONCAT(SUBSTR(REPLACE(date,'Jan','01'),7,4),
       SUBSTR(REPLACE(date,'Jan','01'),4,2),SUBSTR(REPLACE(date,'Jan','01'),1,2))
WHEN date LIKE '%Feb%' THEN CONCAT(SUBSTR(REPLACE(date,'Feb','02'),7,4),
       SUBSTR(REPLACE(date,'Feb','02'),4,2),SUBSTR(REPLACE(date,'Feb','02'),1,2))
WHEN date LIKE '%Mar%' THEN CONCAT(SUBSTR(REPLACE(date,'Mar','03'),7,4),
       SUBSTR(REPLACE(date,'Mar','03'),4,2),SUBSTR(REPLACE(date,'Mar','03'),1,2))
WHEN date LIKE '%Apr%' THEN CONCAT(SUBSTR(REPLACE(date,'Apr','04'),7,4),
       SUBSTR(REPLACE(date,'Apr','04'),4,2),SUBSTR(REPLACE(date,'Apr','04'),1,2))
WHEN date LIKE '%May%' THEN CONCAT(SUBSTR(REPLACE(date,'May','05'),7,4),
       SUBSTR(REPLACE(date,'May','05'),4,2),SUBSTR(REPLACE(date,'May','05'),1,2))
WHEN date LIKE '%Jun%' THEN CONCAT(SUBSTR(REPLACE(date,'Jun','06'),7,4),
       SUBSTR(REPLACE(date,'Jun','06'),4,2),SUBSTR(REPLACE(date,'Jun','06'),1,2))
WHEN date LIKE '%Jul%' THEN CONCAT(SUBSTR(REPLACE(date,'Jul','07'),7,4),
       SUBSTR(REPLACE(date,'Jul','07'),4,2),SUBSTR(REPLACE(date,'Jul','07'),1,2))
WHEN date LIKE '%Aug%' THEN CONCAT(SUBSTR(REPLACE(date,'Aug','08'),7,4),
       SUBSTR(REPLACE(date,'Aug','08'),4,2),SUBSTR(REPLACE(date,'Aug','08'),1,2))
WHEN date LIKE '%Sep%' THEN CONCAT(SUBSTR(REPLACE(date,'Sep','09'),7,4),
       SUBSTR(REPLACE(date,'Sep','09'),4,2),SUBSTR(REPLACE(date,'Sep','09'),1,2))
WHEN date LIKE '%Oct%' THEN CONCAT(SUBSTR(REPLACE(date,'Oct','10'),7,4),
       SUBSTR(REPLACE(date,'Oct','10'),4,2),SUBSTR(REPLACE(date,'Oct','10'),1,2))
WHEN date LIKE '%Nov%' THEN CONCAT(SUBSTR(REPLACE(date,'Nov','11'),7,4),
       SUBSTR(REPLACE(date,'Nov','11'),4,2),SUBSTR(REPLACE(date,'Nov','11'),1,2))
WHEN date LIKE '%Dec%' THEN CONCAT(SUBSTR(REPLACE(date,'Dec','12'),7,4),
       SUBSTR(REPLACE(date,'Dec','12'),4,2),SUBSTR(REPLACE(date,'Dec','12'),1,2))
 END))
group by 1,3,4,5,6
