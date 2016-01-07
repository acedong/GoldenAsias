package com.goldenasia.lottery.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.goldenasia.lottery.R;

public class InfoList {
	
	
	private static InfoList instance = new InfoList();

	private InfoList() {
	}

	public static InfoList getInstance() {
		return instance;
	}
	
	private String[] bankInfoArray=null;

	private List<BankInfo> bankInfoList=new ArrayList<BankInfo>();
	
	private BindingData bindingData=null;
	
	private List<BankSupportInfo> userBankInfoList=new ArrayList<BankSupportInfo>();
	
	private List<ProvinceInfo> provinceInfoList=new ArrayList<ProvinceInfo>();
	
	private Map<String, List<CityInfo>> cityInfoMap=new HashMap<String, List<CityInfo>>();
	
	private List<MyCardInfo> mycardInfoList=new ArrayList<MyCardInfo>();
	
	private Map<String, List<CardLimit>>  cardLimitMap=new HashMap<String, List<CardLimit>>();
	
	private WithdrawalsUserInfo userCashDesk=new WithdrawalsUserInfo();
	
	private static List<LotteryLogo> lotterylogo=Arrays.asList(
			new LotteryLogo("重庆时时彩", R.drawable.id_lottery_cqssc, "10分钟一期"),
			new LotteryLogo("十一运夺金", R.drawable.id_lottery_syydj, "10分钟一期"),
			new LotteryLogo("分分乐", R.drawable.id_lottery_ffl , "自主时时彩，5分钟一期"),
			new LotteryLogo("分分夺金", R.drawable.id_lottery_ffdj, "自主十一选五，5分钟一期"),
			new LotteryLogo("夺金60秒", R.drawable.id_lottery_djlsm, "自主时时彩，1分钟一期"),
			new LotteryLogo("新蜂时时彩", R.drawable.id_lottery_xfssc, "自主时时彩，10分钟一期"),
			new LotteryLogo("新疆时时彩", R.drawable.id_lottery_xjssc, "10分钟一期"));
			
			
	private List<BankInfoLogo> bankinfoLogo = Arrays.asList(
			new BankInfoLogo("中国工商银行", R.drawable.bank_gongshang),
			new BankInfoLogo("中国农业银行", R.drawable.bank_nonghang),
			new BankInfoLogo("中国建设银行", R.drawable.bank_jianhang),
			new BankInfoLogo("中国招商银行", R.drawable.bank_zhaoshang),
			new BankInfoLogo("中国民生银行", R.drawable.bank_ningsheng),
			new BankInfoLogo("中国邮政银行", R.drawable.bank_youzheng),
			new BankInfoLogo("中国北京银行", R.drawable.bank_baijing),
			new BankInfoLogo("中国银行", R.drawable.bank_zhongguo),
			new BankInfoLogo("中国交通银行", R.drawable.bank_jiaotong),
			new BankInfoLogo("中国华夏银行", R.drawable.bank_huaxia),
			new BankInfoLogo("中国信用合作银行", R.drawable.bank_xinghe),
			new BankInfoLogo("中国银联", R.drawable.bank_yinglian),
			new BankInfoLogo("兴业银行", R.drawable.bank_xingyan),
			new BankInfoLogo("上海浦东发展银行", R.drawable.bank_pufa),
			new BankInfoLogo("广东发展银行", R.drawable.bank_guangfa),
			new BankInfoLogo("中国农业发展银行", R.drawable.bank_nongyan),
			new BankInfoLogo("中国光大银行", R.drawable.bank_guangda)
			);
	
	private String addressJsonInfo="{\"citylist\":{"
			+ "\"1\":{\"1\":\"北京市\"},"
			+ "\"2\":{\"19\":\"上海市\"},"
			+ "\"3\":{\"40\":\"天津市\"},"
			+ "\"4\":{\"58\":\"重庆市\"},"
			+ "\"5\":{\"2630\":\"兴安盟\",\"2632\":\"阿拉善盟\",\"2631\":\"锡林郭勒盟\",\"2634\":\"额尔古纳市\",\"112\":\"霍林郭勒市\",\"2633\":\"根河市\",\"2346\":\"乌兰浩特市\",\"110\":\"扎兰屯市\",\"2635\":\"阿尔山市\",\"111\":\"通辽市\",\"2629\":\"乌兰察布市\",\"2628\":\"巴彦淖尔市\",\"2627\":\"呼伦贝尔市\",\"2626\":\"鄂尔多斯市\",\"109\":\"牙克石市\",\"108\":\"满州里市\",\"106\":\"二连浩特市\",\"105\":\"锡林浩特市\",\"104\":\"丰镇市\",\"102\":\"赤峰市\",\"99\":\"呼和浩特市\",\"101\":\"乌海市\",\"100\":\"包头市\"},"
			+ "\"6\":{\"251\":\"汾阳市\",\"205\":\"潞城市\",\"2643\":\"吕梁市\",\"228\":\"原平市\",\"187\":\"忻州市\",\"18\":\"大同市\",\"2314\":\"侯马市\",\"2601\":\"晋中市\",\"189\":\"临汾市\",\"191\":\"霍州市\",\"209\":\"高平市\",\"192\":\"运城市\",\"276\":\"河津市\",\"180\":\"太原市\",\"277\":\"永济市\",\"182\":\"阳泉市\",\"183\":\"长治市\",\"184\":\"晋城市\",\"185\":\"朔州市\",\"186\":\"古交市\",\"242\":\"介休市\",\"254\":\"孝义市\"},"
			+ "\"7\":{\"317\":\"遵化市\",\"2254\":\"晋州市\",\"2253\":\"辛集市\",\"312\":\"迁安市\",\"2268\":\"沙河市\",\"285\":\"邯郸市\",\"284\":\"石家庄市\",\"288\":\"张家口市\",\"289\":\"承德市\",\"2271\":\"南宫市\",\"286\":\"邢台市\",\"287\":\"保定市\",\"300\":\"衡水市\",\"327\":\"三河市\",\"294\":\"武安市\",\"293\":\"廊坊市\",\"2278\":\"深州市\",\"296\":\"泊头市\",\"2277\":\"冀州市\",\"295\":\"霸州市\",\"2241\":\"高碑店市\",\"290\":\"唐山市\",\"2240\":\"涿州市\",\"292\":\"沧州市\",\"291\":\"秦皇岛市\",\"2234\":\"定州市\",\"2235\":\"安国市\",\"2255\":\"藁城市\",\"297\":\"任丘市\",\"2256\":\"新乐市\",\"298\":\"黄骅市\",\"2257\":\"鹿泉市\",\"299\":\"河间市\"},"
			+ "\"8\":{\"2625\":\"大石桥市\",\"384\":\"凤城市\",\"361\":\"阜新市\",\"362\":\"辽阳市\",\"360\":\"营口市\",\"366\":\"海城市\",\"365\":\"瓦房店市\",\"364\":\"朝阳市\",\"389\":\"北镇市\",\"363\":\"铁岭市\",\"369\":\"北票市\",\"368\":\"调兵山市\",\"367\":\"兴城市\",\"2207\":\"盘锦市\",\"2347\":\"盖州市\",\"370\":\"开原市\",\"371\":\"新民市\",\"353\":\"大连市\",\"352\":\"沈阳市\",\"355\":\"抚顺市\",\"354\":\"鞍山市\",\"357\":\"丹东市\",\"404\":\"凌源市\",\"374\":\"庄河市\",\"356\":\"本溪市\",\"359\":\"葫芦岛市\",\"358\":\"锦州市\",\"2535\":\"凌海市\",\"2534\":\"普兰店市\",\"2351\":\"东港市\",\"397\":\"灯塔市\"},"
			+ "\"9\":{\"2623\":\"延边朝鲜族自治州\",\"2354\":\"临江市\",\"453\":\"和龙市\",\"418\":\"蛟河市\",\"436\":\"双辽市\",\"417\":\"桦甸市\",\"433\":\"磐石市\",\"416\":\"九台市\",\"434\":\"舒兰市\",\"415\":\"集安市\",\"431\":\"德惠市\",\"419\":\"榆树市\",\"410\":\"辽源市\",\"414\":\"梅河口市\",\"413\":\"公主岭市\",\"411\":\"通化市\",\"427\":\"龙井市\",\"409\":\"四平市\",\"426\":\"敦化市\",\"408\":\"吉林市\",\"428\":\"珲春市\",\"407\":\"长春市\",\"2352\":\"白山市\",\"421\":\"洮南市\",\"2353\":\"松原市\",\"420\":\"白城市\",\"423\":\"大安市\",\"425\":\"图们市\",\"424\":\"延吉市\"},"
			+ "\"10\":{\"454\":\"哈尔滨市\",\"457\":\"双鸭山市\",\"534\":\"大兴安岭地区\",\"483\":\"讷河市\",\"458\":\"鸡西市\",\"455\":\"齐齐哈尔市\",\"456\":\"鹤岗市\",\"459\":\"大庆市\",\"474\":\"黑河市\",\"499\":\"海林市\",\"473\":\"海伦市\",\"476\":\"五大连池市\",\"475\":\"北安市\",\"501\":\"虎林市\",\"478\":\"双城市\",\"502\":\"宁安市\",\"477\":\"尚志市\",\"461\":\"牡丹江市\",\"500\":\"穆棱市\",\"460\":\"伊春市\",\"462\":\"佳木斯市\",\"463\":\"七台河市\",\"464\":\"绥芬河市\",\"466\":\"同江市\",\"470\":\"绥化市\",\"467\":\"富锦市\",\"471\":\"安达市\",\"468\":\"铁力市\",\"472\":\"肇东市\",\"469\":\"密山市\",\"528\":\"五常市\"},"
			+ "\"11\":{\"2359\":\"邳州市\",\"2624\":\"姜堰市\",\"600\":\"海门市\",\"535\":\"南京市\",\"558\":\"昆山市\",\"559\":\"启东市\",\"609\":\"吴江市\",\"556\":\"淮安市\",\"539\":\"盐城市\",\"557\":\"宜兴市\",\"554\":\"东台市\",\"537\":\"连云港市\",\"606\":\"金坛市\",\"555\":\"兴化市\",\"536\":\"徐州市\",\"552\":\"宿迁市\",\"604\":\"扬中市\",\"553\":\"丹阳市\",\"603\":\"句容市\",\"550\":\"张家港市\",\"551\":\"江阴市\",\"595\":\"靖江市\",\"594\":\"江都市\",\"560\":\"新沂市\",\"596\":\"高邮市\",\"598\":\"如皋市\",\"540\":\"扬州市\",\"542\":\"镇江市\",\"541\":\"南通市\",\"610\":\"太仓市\",\"544\":\"常州市\",\"546\":\"苏州市\",\"545\":\"无锡市\",\"548\":\"仪征市\",\"547\":\"泰州市\",\"549\":\"常熟市\",\"561\":\"溧阳市\",\"2211\":\"通州市\",\"592\":\"泰兴市\",\"589\":\"大丰市\"},"
			+ "\"12\":{\"2366\":\"明光市\",\"2368\":\"宣城市\",\"671\":\"宁国市\",\"2602\":\"池州市\",\"643\":\"桐城市\",\"663\":\"天长市\",\"622\":\"巢湖市\",\"621\":\"滁州市\",\"612\":\"淮南市\",\"620\":\"宿州市\",\"611\":\"合肥市\",\"627\":\"阜阳市\",\"614\":\"芜湖市\",\"626\":\"六安市\",\"613\":\"淮北市\",\"616\":\"蚌埠市\",\"615\":\"铜陵市\",\"618\":\"安庆市\",\"617\":\"马鞍山市\",\"629\":\"界首市\",\"628\":\"毫州市\",\"619\":\"黄山市\"},"
			+ "\"13\":{\"766\":\"禹城市\",\"762\":\"肥城市\",\"748\":\"蓬莱市\",\"747\":\"海阳市\",\"746\":\"栖霞市\",\"745\":\"招远市\",\"726\":\"章丘市\",\"725\":\"临清市\",\"700\":\"济宁市\",\"2209\":\"菏泽市\",\"724\":\"聊城市\",\"2208\":\"潍坊市\",\"722\":\"临沂市\",\"703\":\"青州市\",\"721\":\"滨州市\",\"704\":\"龙口市\",\"720\":\"乐陵市\",\"701\":\"泰安市\",\"702\":\"日照市\",\"707\":\"新泰市\",\"743\":\"寿光市\",\"708\":\"胶州市\",\"742\":\"安丘市\",\"705\":\"曲阜市\",\"706\":\"莱芜市\",\"696\":\"东营市\",\"2396\":\"邹城市\",\"695\":\"枣庄市\",\"694\":\"淄博市\",\"693\":\"青岛市\",\"750\":\"乳山市\",\"751\":\"兖州市\",\"699\":\"威海市\",\"698\":\"烟台市\",\"709\":\"诸城市\",\"717\":\"莱西市\",\"716\":\"平度市\",\"737\":\"昌邑市\",\"719\":\"德州市\",\"718\":\"胶南市\",\"739\":\"高密市\",\"713\":\"文登市\",\"712\":\"滕州市\",\"715\":\"即墨市\",\"714\":\"荣成市\",\"711\":\"莱州市\",\"710\":\"莱阳市\",\"692\":\"济南市\"},"
			+ "\"14\":{\"843\":\"乐清市\",\"824\":\"临海市\",\"806\":\"湖州市\",\"807\":\"绍兴市\",\"822\":\"诸暨市\",\"804\":\"温州市\",\"805\":\"嘉兴市\",\"802\":\"杭州市\",\"829\":\"临安市\",\"803\":\"宁波市\",\"826\":\"丽水市\",\"827\":\"龙泉市\",\"869\":\"温岭市\",\"848\":\"桐乡市\",\"820\":\"慈溪市\",\"846\":\"平湖市\",\"821\":\"奉化市\",\"2361\":\"嵊州市\",\"819\":\"义乌市\",\"811\":\"余姚市\",\"812\":\"海宁市\",\"832\":\"建德市\",\"813\":\"兰溪市\",\"831\":\"富阳市\",\"814\":\"瑞安市\",\"816\":\"江山市\",\"818\":\"东阳市\",\"855\":\"上虞市\",\"858\":\"永康市\",\"810\":\"舟山市\",\"2216\":\"台州市\",\"809\":\"衢州市\",\"808\":\"金华市\"},"
			+ "\"15\":{\"2377\":\"抚州市\",\"2376\":\"井冈山市\",\"890\":\"樟树市\",\"957\":\"南康市\",\"964\":\"瑞金市\",\"925\":\"高安市\",\"911\":\"贵溪市\",\"899\":\"乐平市\",\"888\":\"宜春市\",\"879\":\"南昌市\",\"889\":\"丰城市\",\"884\":\"鹰潭市\",\"894\":\"赣州市\",\"885\":\"瑞昌市\",\"886\":\"上饶市\",\"892\":\"吉安市\",\"887\":\"德兴市\",\"880\":\"景德镇市\",\"881\":\"萍乡市\",\"882\":\"新余市\",\"883\":\"九江市\"},"
			+ "\"16\":{\"977\":\"福清市\",\"976\":\"石狮市\",\"979\":\"邵武市\",\"978\":\"南平市\",\"969\":\"福州市\",\"1007\":\"南安市\",\"1008\":\"晋江市\",\"982\":\"福安市\",\"981\":\"宁德市\",\"980\":\"武夷山市\",\"971\":\"三明市\",\"970\":\"厦门市\",\"973\":\"泉州市\",\"972\":\"莆田市\",\"984\":\"漳平市\",\"975\":\"永安市\",\"1023\":\"建瓯市\",\"983\":\"龙岩市\",\"974\":\"漳州市\",\"1032\":\"福鼎市\",\"1020\":\"建阳市\",\"991\":\"长乐市\",\"1011\":\"龙海市\"},"
			+ "\"17\":{\"2621\":\"湘西土家族苗族自治州\",\"2622\":\"汨罗市\",\"1048\":\"湘乡市\",\"1047\":\"醴陵市\",\"1046\":\"张家界市\",\"1076\":\"武冈市\",\"1045\":\"常德市\",\"1051\":\"津市市\",\"1054\":\"资兴市\",\"1068\":\"浏阳市\",\"1055\":\"永州市\",\"1052\":\"韶山市\",\"1053\":\"郴州市\",\"1080\":\"邵阳市\",\"2421\":\"常宁市\",\"1064\":\"吉首市\",\"1063\":\"沅江市\",\"1062\":\"益阳市\",\"1061\":\"洪江市\",\"1086\":\"临湘市\",\"1060\":\"怀化市\",\"2416\":\"耒阳市\",\"1040\":\"长沙市\",\"1058\":\"冷水江市\",\"1041\":\"株洲市\",\"1059\":\"涟源市\",\"1042\":\"湘潭市\",\"1043\":\"衡阳市\",\"1057\":\"娄底市\",\"1044\":\"岳阳市\"},"
			+ "\"18\":{\"1169\":\"利川市\",\"2620\":\"赤壁市\",\"1168\":\"恩施市\",\"1167\":\"丹江口市\",\"1149\":\"孝感市\",\"1197\":\"钟祥市\",\"1166\":\"十堰市\",\"1148\":\"枣阳市\",\"1147\":\"老河口市\",\"1146\":\"随州市\",\"1145\":\"鄂州市\",\"1144\":\"荆门市\",\"1153\":\"麻城市\",\"1154\":\"武穴市\",\"1151\":\"安陆市\",\"1152\":\"广水市\",\"1150\":\"应城市\",\"2619\":\"宜都市\",\"1156\":\"咸宁市\",\"1138\":\"武汉市\",\"1158\":\"仙桃市\",\"1139\":\"黄石市\",\"1159\":\"石首市\",\"1181\":\"汉川市\",\"2618\":\"恩施土家族苗族自治州\",\"1180\":\"神农架林区\",\"1206\":\"枝江市\",\"2410\":\"大冶市\",\"2412\":\"黄冈市\",\"1201\":\"松滋市\",\"1140\":\"襄樊市\",\"1160\":\"天门市\",\"1142\":\"荆州市\",\"1161\":\"洪湖市\",\"1162\":\"潜江市\",\"1163\":\"宜昌市\",\"1165\":\"当阳市\"},"
			+ "\"19\":{\"1295\":\"长葛市\",\"1231\":\"三门峡市\",\"1230\":\"漯河市\",\"1245\":\"邓州市\",\"1227\":\"安阳市\",\"1246\":\"荥阳市\",\"1228\":\"濮阳市\",\"2403\":\"林州市\",\"1243\":\"信阳市\",\"1225\":\"鹤壁市\",\"1244\":\"南阳市\",\"1226\":\"新乡市\",\"1249\":\"新郑市\",\"1223\":\"平顶山市\",\"1224\":\"焦作市\",\"1247\":\"登封市\",\"1221\":\"开封市\",\"1222\":\"洛阳市\",\"2398\":\"巩义市\",\"1229\":\"许昌市\",\"1308\":\"永城市\",\"2616\":\"新密市\",\"2617\":\"孟州市\",\"1260\":\"偃师市\",\"1220\":\"郑州市\",\"1300\":\"灵宝市\",\"1240\":\"商丘市\",\"1320\":\"项城市\",\"1242\":\"驻马店市\",\"1241\":\"周口市\",\"1232\":\"义马市\",\"1233\":\"汝州市\",\"1234\":\"济源市\",\"1235\":\"禹州市\",\"1236\":\"卫辉市\",\"1237\":\"辉县市\",\"1238\":\"沁阳市\",\"1239\":\"舞钢市\"},"
			+ "\"20\":{\"1411\":\"开平市\",\"1370\":\"从化市\",\"1352\":\"珠海市\",\"1410\":\"台山市\",\"1351\":\"深圳市\",\"1350\":\"广州市\",\"1434\":\"云浮市\",\"1433\":\"罗定市\",\"2607\":\"雷州市\",\"1431\":\"四会市\",\"1413\":\"鹤山市\",\"1412\":\"恩平市\",\"1371\":\"增城市\",\"1419\":\"阳春市\",\"1439\":\"英德市\",\"1389\":\"乐昌市\",\"1366\":\"肇庆市\",\"1367\":\"清远市\",\"1364\":\"湛江市\",\"1365\":\"茂名市\",\"1368\":\"潮州市\",\"1420\":\"吴川市\",\"2425\":\"连州市\",\"1424\":\"廉江市\",\"1361\":\"江门市\",\"1360\":\"中山市\",\"1426\":\"信宜市\",\"1363\":\"阳江市\",\"1425\":\"高州市\",\"1408\":\"陆丰市\",\"1362\":\"佛山市\",\"1428\":\"化州市\",\"1382\":\"揭阳市\",\"1384\":\"南雄市\",\"1429\":\"高要市\",\"2428\":\"普宁市\",\"1400\":\"兴宁市\",\"1353\":\"汕头市\",\"1354\":\"韶关市\",\"1355\":\"河源市\",\"1356\":\"梅州市\",\"1357\":\"惠州市\",\"1358\":\"汕尾市\",\"1359\":\"东莞市\"},"
			+ "\"21\":{\"1446\":\"海口市\",\"1448\":\"五指山市\",\"1447\":\"三亚市\",\"2615\":\"儋州市\",\"1452\":\"万宁市\",\"1460\":\"东方市\",\"1450\":\"文昌市\",\"1451\":\"琼海市\"},"
			+ "\"22\":{\"1488\":\"崇左市\",\"1475\":\"河池市\",\"1474\":\"百色市\",\"1469\":\"北海市\",\"2608\":\"宜州市\",\"2435\":\"防城港市\",\"1468\":\"梧州市\",\"2609\":\"东兴市\",\"1467\":\"桂林市\",\"1466\":\"柳州市\",\"1465\":\"南宁市\",\"1497\":\"来宾市\",\"1522\":\"北流市\",\"1521\":\"桂平市\",\"1472\":\"玉林市\",\"1473\":\"贵港市\",\"2430\":\"钦州市\",\"1470\":\"凭祥市\",\"1471\":\"合山市\",\"1517\":\"贺州市\",\"1515\":\"岑溪市\"},"
			+ "\"23\":{\"1550\":\"铜仁市\",\"2611\":\"黔东南苗族侗族自治州\",\"2612\":\"黔南布依族苗族自治州\",\"2613\":\"铜仁地区\",\"2614\":\"毕节地区\",\"2610\":\"黔西南布依族苗族自治州\",\"1548\":\"遵义市\",\"1594\":\"清镇市\",\"1581\":\"毕节市\",\"1549\":\"赤水市\",\"1627\":\"福泉市\",\"1555\":\"都匀市\",\"1553\":\"兴义市\",\"1563\":\"仁怀市\",\"1554\":\"凯里市\",\"1546\":\"贵阳市\",\"1552\":\"安顺市\",\"1547\":\"六盘水市\"},"
			+ "\"24\":{\"2647\":\"华蓥市\",\"2646\":\"阆中市\",\"2645\":\"达州市\",\"2459\":\"峨眉山市\",\"2648\":\"万源市\",\"1648\":\"崇州市\",\"1647\":\"彭州市\",\"1649\":\"都江堰市\",\"1644\":\"广汉市\",\"1643\":\"广安市\",\"1646\":\"江油市\",\"1645\":\"西昌市\",\"1640\":\"乐山市\",\"1642\":\"宜宾市\",\"1641\":\"南充市\",\"2489\":\"资阳市\",\"2467\":\"简阳市\",\"1652\":\"雅安市\",\"2472\":\"绵竹市\",\"1653\":\"眉山市\",\"2473\":\"什邡市\",\"1651\":\"巴中市\",\"1656\":\"凉山彝族自治州\",\"1639\":\"内江市\",\"1638\":\"遂宁市\",\"1654\":\"阿坝藏族羌族自治州\",\"1637\":\"广元市\",\"1655\":\"甘孜藏族自治州\",\"1636\":\"绵阳市\",\"1635\":\"德阳市\",\"1634\":\"泸州市\",\"1633\":\"攀枝花市\",\"1632\":\"自贡市\",\"1631\":\"成都市\",\"2452\":\"邛崃市\"},"
			+ "\"25\":{\"2670\":\"西双版纳傣族自治州\",\"2672\":\"怒江傈僳族自治州\",\"1749\":\"景洪市\",\"2671\":\"德宏傣族景颇族自治州\",\"1772\":\"潞西市\",\"1775\":\"瑞丽市\",\"1661\":\"昭通市\",\"1688\":\"宣威市\",\"1662\":\"曲靖市\",\"2677\":\"大理白族自治州\",\"1728\":\"丽江市\",\"1663\":\"玉溪市\",\"1664\":\"保山市\",\"2675\":\"红河哈尼族彝族自治州\",\"1665\":\"个旧市\",\"2676\":\"楚雄彝族自治州\",\"1714\":\"临沧市\",\"1675\":\"安宁市\",\"1666\":\"开远市\",\"2673\":\"迪庆藏族自治州\",\"1706\":\"普洱市\",\"1667\":\"楚雄市\",\"2674\":\"文山壮族苗族自治州\",\"1668\":\"大理市\",\"1659\":\"昆明市\"},"
			+ "\"26\":{\"2644\":\"商洛市\",\"2505\":\"铜川市\",\"1790\":\"延安市\",\"1792\":\"韩城市\",\"1820\":\"兴平市\",\"1791\":\"渭南市\",\"1785\":\"西安市\",\"1795\":\"安康市\",\"1796\":\"汉中市\",\"1787\":\"宝鸡市\",\"1793\":\"华阴市\",\"1788\":\"咸阳市\",\"1789\":\"榆林市\"},"
			+ "\"27\":{\"1894\":\"临夏市\",\"1920\":\"庆阳市\",\"1892\":\"酒泉市\",\"2606\":\"合作市\",\"1889\":\"武威市\",\"2604\":\"临夏回族自治州\",\"2605\":\"甘南藏族自治州\",\"1886\":\"嘉峪关市\",\"2603\":\"陇南市\",\"1885\":\"天水市\",\"1887\":\"平凉市\",\"1882\":\"兰州市\",\"1884\":\"白银市\",\"1907\":\"定西市\",\"1883\":\"金昌市\",\"1890\":\"张掖市\",\"1891\":\"玉门市\"},"
			+ "\"28\":{\"1978\":\"固原市\",\"1965\":\"石嘴山市\",\"1964\":\"银川市\",\"1967\":\"青铜峡市\",\"1966\":\"吴忠市\",\"1975\":\"灵武市\",\"1976\":\"中卫市\"},"
			+ "\"29\":{\"1986\":\"德令哈市\",\"2642\":\"冷湖行委\",\"2641\":\"海西蒙古族藏族自治州\",\"2636\":\"海北藏族自治州\",\"1984\":\"西宁市\",\"2640\":\"玉树藏族自治州\",\"1985\":\"格尔木市\",\"2638\":\"海南藏族自治州\",\"2637\":\"黄南藏族自治州\",\"1988\":\"海东地区\",\"2639\":\"果洛藏族自治州\"},"
			+ "\"30\":{\"2030\":\"哈密市\",\"2031\":\"和田市\",\"2032\":\"阿克苏市\",\"2033\":\"喀什市\",\"2034\":\"阿图什市\",\"2525\":\"阿拉尔市\",\"2104\":\"乌苏市\",\"2027\":\"克拉玛依市\",\"2026\":\"乌鲁木齐市\",\"2029\":\"吐鲁番市\",\"2028\":\"石河子市\",\"2520\":\"五家渠市\",\"2656\":\"巴音郭楞蒙古自治州\",\"2040\":\"塔城市\",\"2655\":\"博尔塔拉蒙古自治州\",\"2041\":\"阿勒泰市\",\"2658\":\"伊犁哈萨克自治州\",\"2657\":\"克孜勒苏柯尔克孜自治州\",\"2085\":\"阜康市\",\"2659\":\"图木舒克市\",\"2666\":\"塔城地区\",\"2667\":\"阿勒泰地区\",\"2668\":\"吐鲁番地区\",\"2519\":\"奎屯市\",\"2669\":\"博乐市\",\"2662\":\"和田地区\",\"2663\":\"阿克苏地区\",\"2664\":\"喀什地区\",\"2665\":\"昌吉回族自治州\",\"2037\":\"博乐市\",\"2660\":\"吐鲁番地区\",\"2036\":\"昌吉市\",\"2661\":\"哈密地区\",\"2035\":\"库尔勒市\",\"2039\":\"伊宁市\"},"
			+ "\"31\":{\"2133\":\"昌都县\",\"2652\":\"日喀则地区\",\"2654\":\"昌都地区\",\"2653\":\"那曲地区\",\"2527\":\"山南地区\",\"2650\":\"林芝地区\",\"2114\":\"拉萨市\",\"2186\":\"林芝县\",\"2115\":\"日喀则市\",\"2649\":\"阿里地区\",\"2123\":\"那曲县\"},"
			+ "\"32\":{\"2530\":\"香港地区\"},"
			+ "\"33\":{\"2531\":\"澳门地区\"},"
			+ "\"34\":{\"2532\":\"台湾全省\"}},"
			+ "\"provincelist\":{\"19\":\"河南\",\"17\":\"湖南\",\"18\":\"湖北\",\"15\":\"江西\",\"16\":\"福建\",\"13\":\"山东\",\"14\":\"浙江\",\"11\":\"江苏\",\"12\":\"安徽\",\"21\":\"海南\",\"20\":\"广东\",\"22\":\"广西\",\"23\":\"贵州\",\"24\":\"四川\",\"25\":\"云南\",\"26\":\"陕西\",\"27\":\"甘肃\",\"28\":\"宁夏\",\"29\":\"青海\",\"3\":\"天津\",\"2\":\"上海\",\"10\":\"黑龙江\",\"1\":\"北京\",\"30\":\"新疆\",\"7\":\"河北\",\"6\":\"山西\",\"5\":\"内蒙古\",\"31\":\"西藏\",\"4\":\"重庆\",\"9\":\"吉林\",\"8\":\"辽宁\"}}";
	
	public InfoList(List<BankInfo> bankInfoList,BindingData bindingData,List<BankSupportInfo> userBankInfoList){
		this.bankInfoList=bankInfoList;
		this.bindingData = bindingData;
		this.userBankInfoList = userBankInfoList;
	}

	public List<BankInfo> getBankInfoList() {
		return bankInfoList;
	}

	public void setBankInfoList(List<BankInfo> bankInfoList) {
		this.bankInfoList = bankInfoList;
	}
	
	public String[] getBankInfoArray() {
		return bankInfoArray;
	}

	public void setBankInfoArray(String[] bankInfoArray) {
		this.bankInfoArray = bankInfoArray;
	}

	public void InitBankData()
	{
		this.bankInfoArray=new String[getBankInfoList().size()];
		for(int i=0; i<getBankInfoList().size();i++)
		{
			BankInfo bankinfo=getBankInfoList().get(i);
			this.bankInfoArray[i]=bankinfo.getBankname();
		}
	}

	public BankInfo isSelectBank(String name){
		BankInfo info=new BankInfo();
		for(int i=0; i<getBankInfoList().size();i++)
		{
			BankInfo bankinfo=getBankInfoList().get(i);
			if(bankinfo.getBankname().indexOf(name.trim())!=-1)
			{
				info=bankinfo;
			}
		}
		return info;
	}
	
	public BindingData getBindingData() {
		return bindingData;
	}

	public void setBindingData(BindingData bindingData) {
		this.bindingData = bindingData;
	}
	
	public List<BankSupportInfo> getUserBankInfoList() {
		return userBankInfoList;
	}

	public void setUserBankInfoList(List<BankSupportInfo> userBankInfoList) {
		this.userBankInfoList = userBankInfoList;
	}

	public List<ProvinceInfo> getProvinceInfoList() {
		return provinceInfoList;
	}

	private void setProvinceInfoList(List<ProvinceInfo> provinceInfoList) {
		this.provinceInfoList = provinceInfoList;
	}

	public Map<String, List<CityInfo>> getCityInfoMap() {
		return cityInfoMap;
	}

	private void setCityInfoMap(Map<String, List<CityInfo>> cityInfoMap) {
		this.cityInfoMap = cityInfoMap;
	}
	
	public void InitAddress(){
		
		try {
			JSONTokener jsonParser = new JSONTokener(addressJsonInfo);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			List<ProvinceInfo> prInfoList=new ArrayList<ProvinceInfo>();
			JSONObject provinceInfoJson=jsonObject.getJSONObject("provincelist");
			for(int p=0; p<provinceInfoJson.length();p++)
			{
				String proId=provinceInfoJson.names().getString(p);
				ProvinceInfo provInfo=new ProvinceInfo();
				provInfo.setProvinceid(proId);
				provInfo.setProvinceName(provinceInfoJson.getString(proId));
				prInfoList.add(provInfo);
			}
			
			JSONObject cityInfoJson=jsonObject.getJSONObject("citylist");
			Map<String, List<CityInfo>> ciInfoMap=new HashMap<String, List<CityInfo>>();
			
			for(int p=0;p<cityInfoJson.length();p++){
				String pId=cityInfoJson.names().getString(p);
				JSONObject cityJsonObj=cityInfoJson.getJSONObject(pId);
				List<CityInfo> ciInfoLsit= new ArrayList<CityInfo>();
				for(int c=0;c<cityJsonObj.length();c++)
				{
					String cId=cityJsonObj.names().getString(c);
					CityInfo ciInfo=new CityInfo();
					ciInfo.setCityId(cId);
					ciInfo.setCityName(cityJsonObj.getString(cId));
					ciInfoLsit.add(ciInfo);
				}
				ciInfoMap.put(pId, ciInfoLsit);
			}
			
			InfoList.getInstance().setProvinceInfoList(prInfoList);
			InfoList.getInstance().setCityInfoMap(ciInfoMap);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ProvinceInfo infoProvince=null; 
	
	public ProvinceInfo getInfoProvince() {
		return infoProvince;
	}

	public void setInfoProvince(ProvinceInfo infoProvince) {
		this.infoProvince = infoProvince;
	}

	public ProvinceInfo isSelectProvince(String name){
		String provinceName=name.substring(0,name.length()-1);
		ProvinceInfo info=new ProvinceInfo();
		for(int p=0;p<InfoList.getInstance().getProvinceInfoList().size();p++){
			ProvinceInfo pro=InfoList.getInstance().getProvinceInfoList().get(p);
			if(pro.getProvinceName().indexOf(provinceName.trim())!=-1){
				info=pro;
				setInfoProvince(pro);
			}
		}
		return info;
	}
	
	public CityInfo isSelectCity(String name){
		CityInfo info=new CityInfo();
		
		List<CityInfo> cities = cityInfoMap.get(getInfoProvince().getProvinceid());
		for(int c=0;c<cities.size();c++)
		{
			CityInfo ci=cities.get(c);
			if(ci.getCityName().indexOf(name.trim())!=-1){
				info=ci;
			}
		}
		return info;
	}
	
	public CityInfo isSelectCity(ProvinceInfo province,String name){
		CityInfo info=new CityInfo();
		List<CityInfo> cities = cityInfoMap.get(province.getProvinceid());
		for(int c=0;c<cities.size();c++)
		{
			CityInfo ci=cities.get(c);
			if(ci.getCityName().indexOf(name.trim())!=-1){
				info=ci;
			}
		}
		return info;
	}

	public List<MyCardInfo> getMycardInfoList() {
		return mycardInfoList;
	}

	public void setMycardInfoList(List<MyCardInfo> mycardInfoList) {
		this.mycardInfoList = mycardInfoList;
	}
	
	public String[] getMycardInfoData(){
		String[] mycardData=new String[mycardInfoList.size()];
		
		for(int i=0;i<mycardInfoList.size();i++){
			MyCardInfo cardinfo=mycardInfoList.get(i);
			mycardData[i]=cardinfo.getBankname()+"尾号"+cardinfo.getCardno();
		}
		return mycardData;
	}
	
	public MyCardInfo getChooseMycardInfoData(String cardno){
		MyCardInfo mycard=null;
		for(int i=0;i<mycardInfoList.size();i++){
			MyCardInfo cardinfo=mycardInfoList.get(i);
			if(cardno.equals(cardinfo.getCardno()))
				mycard=cardinfo;
		}
		return mycard;
	}

	public Map<String, List<CardLimit>> getCardLimitMap() {
		return cardLimitMap;
	}

	public void setCardLimitMap(Map<String, List<CardLimit>> cardLimitMap) {
		this.cardLimitMap = cardLimitMap;
	}

	public WithdrawalsUserInfo getUserCashDesk() {
		return userCashDesk;
	}

	public void setUserCashDesk(WithdrawalsUserInfo userCashDesk) {
		this.userCashDesk = userCashDesk;
	}

	public List<BankInfoLogo> getBankinfoLogo() {
		return bankinfoLogo;
	}

	public BankInfoLogo getBankinfoLogo(String bankname) {
		List<BankInfoLogo> banklogoList=this.bankinfoLogo;
		BankInfoLogo logo=null;
		for(int i=0;i<banklogoList.size();i++){
			BankInfoLogo banklogo=banklogoList.get(i);
			if(bankname.equals(banklogo.getBankName()))
			{
				logo=banklogo;
			}
		}
		return logo;
	}
	
	public static int gatheringLogo(String lotteryName){
		int logoid=0;
		for(int l=0;l<lotterylogo.size(); l++){
			LotteryLogo logo=lotterylogo.get(l);
			if(lotteryName.equals(logo.getLotteryName())){
				logoid=logo.getLotteryLogo();
			}
		}
		return logoid;
	}
	
	public static String gatheringPrompt(String lotteryName){
		String prompt="";
		for(int l=0;l<lotterylogo.size(); l++){
			LotteryLogo logo=lotterylogo.get(l);
			if(lotteryName.equals(logo.getLotteryName())){
				prompt=logo.getLotteryPrompt();
			}
		}
		return prompt;
	}
	
	public void clear()
	{
		this.bankInfoArray=null;
		this.bankInfoList=new ArrayList<BankInfo>();
		this.bindingData=null;
		this.userBankInfoList=new ArrayList<BankSupportInfo>();
		this.provinceInfoList=new ArrayList<ProvinceInfo>();
		this.cityInfoMap=new HashMap<String, List<CityInfo>>();
		this.mycardInfoList=new ArrayList<MyCardInfo>();
		this.cardLimitMap=new HashMap<String, List<CardLimit>>();
		this.userCashDesk=new WithdrawalsUserInfo();
		this.bankinfoLogo = new ArrayList<BankInfoLogo>();
	}
}
