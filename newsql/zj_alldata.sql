REPLACE INTO zj_alldata
SELECT a.id,FROM_UNIXTIME(a.create_time),a.`type`,a.`number`,a.dev_sn,a.profit,
       b.uid,b.openid,b.nickname,b.sex,b.phone,b.phone_province,b.phone_city,b.phone_isp,b.headimgurl,q.head_md5,q.head_date,b.unionid,b.country,b.province,b.city,b.paper_count,b.paper_number,b.app_login_time,b.state,FROM_UNIXTIME(b.create_time),
       e.id,e.nickname,e.mobile,m.name,n.name,o.name,
       c.imei,c.name,g.name,h.name,i.name,c.latitude,c.longitude,c.join_net,c.is_close,
       d.id,d.name,d.contact,d.tel,j.name,k.name,l.name,d.latitude,d.longitude,d.specific_address,d.position_name,
       f.id,f.nick_name,f.device_list,
       p.ip,p.logtime,p.phone_sysyem,p.phone_type,p.network,p.phone_browser,p.wechat_version,p.logdate,
       r.country,r.province,r.city,r.ips
from zj_order a
LEFT JOIN zj_dmp_user b
ON a.user_id = b.`uid`
LEFT JOIN zj_device c
ON a.dev_id = c.id
LEFT JOIN zj_store d
ON a.store_id = d.id
LEFT JOIN zj_agent e
ON a.agent_id = e.id
LEFT JOIN zj_wechat_authorizer f
ON a.authorizer_id = f.id
LEFT JOIN logs_dwd p
ON a.user_id = p.user_id
LEFT JOIN zj_area_city g
ON c.province_id = g.code
LEFT JOIN zj_area_city h
ON c.city_id = h.code
LEFT JOIN zj_area_city i
ON c.area_id = i.code
LEFT JOIN zj_area_city j
ON d.province_id = j.code
LEFT JOIN zj_area_city k
ON d.city_id = k.code
LEFT JOIN zj_area_city l
ON d.area_id = l.code
LEFT JOIN zj_area_city m
ON e.province_id = m.code
LEFT JOIN zj_area_city n
ON e.city_id = n.code
LEFT JOIN zj_area_city o
ON e.area_id = o.code
LEFT JOIN zj_user_head q
on a.user_id=q.head_user_id
LEFT JOIN zj_ipsdata r
on a.id = r.id
where date_add(curdate(), interval -1 day) = DATE(FROM_UNIXTIME(a.create_time))
and date_add(curdate(), interval -1 day) = DATE(p.logdate)
