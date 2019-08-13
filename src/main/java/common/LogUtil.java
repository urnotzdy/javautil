package common;

import com.alibaba.fastjson.JSON;

/**
 * @Description 日志工具类
 * @Author zhangdanyang02
 * @Date 2019/7/15 20:29
 **/
public class LogUtil {

    /**
     * 获取错误日志
     *
     * @param e Exception
     * @return string类型的错误日志
     */
    public static String getErrorMsg(Exception e) {
        String error = e.toString() + "\r\n";
        StackTraceElement[] traceElements = e.getStackTrace();
        for (StackTraceElement traceElement : traceElements) {
            error += "\tat" + traceElement + "\r\n";
        }
        return error;
    }

    public static void main(String[] args) {
        String infp="{\"copyright\":\"0\",\"publishtype\":\"0\",\"ht_token\":\"BAEA3F89C6B8989310\n" +
                "412C5F4B974F1059C8D9C6B47CA9E77DA05DC77A09A5B8\",\"tasksource\":\"12001\",\"entityid\":\"34347138\",\"isvideo\":\"0\",\"isreport\":\"0\",\"sessionid\":\"35cc48f5-ace\n" +
                "f-45fd-b638-d6f8b2b28069\",\"pic\":\"http://pic1.58cdn.com.cn/nowater/58toutiao/small/n_v249a986bc5d5142d5b277f42a9121fbb6.gif?t=1&w1=180&h1=180\",\"ti\n" +
                "tle\":\"公摊面积！再见？央媒集体发声！赤峰买房的赶紧看！\",\"userid\":\"52878698377238\",\"content\":\"公摊面积！再见？央媒集体发声！赤峰买房的赶紧看！ \",\"\n" +
                "producttype\":\"2\",\"province\":\"20010\",\"addtime\":\"2018-08-14 04:24:31\",\"sceneid\":\"164\",\"userip\":\"\",\"srcport\":\"\",\"sendtime\":\"2019-07-26 18:28:22\"}\n" +
                "[07-26 18:39:50 174 INFO ] [Thread-41] kingyu.task.TaskHandler - taskId:61,data:{\"copyright\":\"0\",\"publishtype\":\"0\",\"ht_token\":\"268CA1567190E41B35\n" +
                "23BB3FD204A32D7919414661021389BA73CC1F37BFB944\",\"tasksource\":\"12001\",\"entityid\":\"34356061\",\"isvideo\":\"0\",\"isreport\":\"0\",\"sessionid\":\"424b8d6e-fca\n" +
                "f-4b2d-807c-b34bd0aca369\",\"pic\":\"http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v2b71d138b338c4a18872219b4f8b5ccc9.jpg|http://pic1.58cdn.com.cn\n" +
                "/nowater/58toutiao/big/n_v236535c4c0d0a4dc48f4ac3d79e217f6a.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v241711451e78c40c5b8d8cd31ceabf2\n" +
                "ac.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v262f68b3f79cd42bd9b176e19103ee346.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v\n" +
                "2366a2ddbde5a4805a600251b3115364a.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v2775efdf0f0034fa8a124bf257033cba6.jpg|http://pic1.58cdn.c\n" +
                "om.cn/nowater/58toutiao/big/n_v2dad5d3cafb834902b3a92f38bd0c9226.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v2d3ea567388fe463aa4531603f\n" +
                "358d54f.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v2a17a8db2d3d443c3a98729ad4f235425.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/bi\n" +
                "g/n_v2b881efb5638b4d5c922424a1a922ddec.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v280bbeae51cde4dbdacbcab380a561690.jpg|http://pic1.58\n" +
                "cdn.com.cn/nowater/58toutiao/big/n_v22c71871ad3c64cdebfe775be9f94d3f1.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v21ec03cac2eb244d7928f\n" +
                "da308e26f344.jpg|http://pic1.58cdn.com.cn/nowater/58toutiao/big/n_v2525b67796a79495885bebab8f56936d0.jpg|http://pic1.58cdn.com.cn/nowater/58touti\n" +
                "ao/big/n_v21e22fd8d53cd4bbda8309755faa13413.gif||http://pic1.58cdn.com.cn/nowater/58toutiao/small/n_v2b71d138b338c4a18872219b4f8b5ccc9.jpg?t=1&w1\n" +
                "=752&h1=418\",\"title\":\"来了！新闻早班车\",\"userid\":\"50735522737428\",\"content\":\"来了！新闻早班车  \\n \\n \\n \\n  \\n   \\n   \\n   \\n  \\n  \\n  \\n    要闻\n" +
                " \\n   \\n  \\n  \\n \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       受台风影响，东北地区、天津、河北、山西、河南、山东、安徽、广\n" +
                "东、海南岛等地有大到暴雨，局地伴有短时强降水、雷暴大风等强对流天气。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n   \n" +
                "  \\n     \\n       国办印发方案，中央将制定计划生育扶助保障补助国家基础标准。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n \n" +
                "   \\n     \\n     \\n       最高法下发通知，要求加大对借贷事实和证据的审查力度，严格区分民间借贷行为与诈骗等犯罪行为。 \\n      \\n     \\n    \\n   \\n\n" +
                "  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       昨日，国家林业和草原局公布内蒙古自治区阿拉善右旗阿拉腾朝格等31个国家沙\n" +
                "土地封禁保护区。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       央行发布报告，提出加强电子支付管理\n" +
                "和风险防范，严厉打击破坏支付安全的行为。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       近日，藏中\n" +
                "联网工程第一座500千伏变电站——芒康变电站带电运行。变电站位于海拔4300米的拉乌山，是目前世界上海拔最高的500千伏变电站。 \\n      \\n     \\n    \\n   \\n\n" +
                "  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       云南玉溪通海县13日凌晨发生5.0级地震。截至昨晚，地震造成18人受伤，暂无人\n" +
                "员死亡。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       台湾新北市一家医院13日发生大火，已\n" +
                "造成9人死亡、15人受伤。国台办对不幸遇难的台湾同胞表示哀悼，向遇难者家属及受伤人员表示诚挚慰问。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \n" +
                "\\n    \\n   \\n   \\n    \\n     \\n     \\n       朝鲜和韩国13日举行高级别会谈，同意9月在平壤再度举行朝韩首脑会晤。 \\n      \\n     \\n    \\n   \\n  \\n  \n" +
                "\\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       阿富汗国防部官员13日表示，政府军连日来与塔利班武装人员发生持续交火，已至少打死194名武\n" +
                "装分子。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       印尼国家减灾局13日消息，龙目岛地震已造成43\n" +
                "6人死亡，259人的身份得到确认，已发生大小593次余震。 \\n      \\n     \\n    \\n   \\n  \\n \\n \\n  \\n \\n \\n \\n  \\n   \\n   \\n   \\n  \\n  \\n  \\n    社会 \\n\n" +
                "   \\n  \\n  \\n \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       内蒙古通辽发生疑似牛炭疽疫情，官方通报，截至12日，发病牛死亡9头\n" +
                "，8人感染疑似皮肤炭疽，均入院有效治疗，目前病情稳定。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n    \n" +
                "   南京海关打掉两个特大走私成品油的犯罪团伙，抓获嫌疑人38名。初步查证去年以来，他们走私成品油10万多吨，案值6.5亿多元。 \\n      \\n     \\n    \\n   \n" +
                "\\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       12日，上海一商铺招牌脱落砸中路人，导致3人死亡、6人受伤。安监部门已介\n" +
                "调查。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       网传“西安白鹿仓景区有人穿日伪军服表\n" +
                "”的视频，景区昨日回应，表演已取消，实为百姓智斗日伪军的故事。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n   \n" +
                "  \\n       深圳游客崔某因意见纠纷辱骂张家界人，遭500多人围堵。警方13日公布，崔某已写信向张家界人民致歉，涉嫌寻衅滋事的16人被刑拘。 \\n      \\n    \n" +
                " \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       湖北情侣高某、张某生下二胎后无力抚养，联系中介将孩子以4.5\n" +
                "万元卖给他人。两人行为涉嫌拐卖儿童犯罪，被警方抓获。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n    \n" +
                " \\n       广西南宁吴圩国际机场，乘客那某怕赶不上飞机，谎称机上有炸弹，导致航班延误。那某被处以5日治安拘留。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \n" +
                "\\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       南京男子林某为逼父母过户房子，爬上15楼楼顶扬言跳楼。因扰乱公共秩序，林某被行政拘留3日。 \n" +
                "\\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       江西南昌老人徐雪英曾作为保姆照顾过熊桂兰，\n" +
                "今老人102岁，膝下无儿无女。熊桂兰将她接到家里悉心照顾，为她养老送终。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n  \n" +
                "   \\n      \\n     \\n       浙江大学15名学生暑期自费赴河南汝阳县蔡店镇支教，因天气炎热，有人中暑病倒。村民们为他们送去饮料、西瓜，并凑钱装空调。 \\\n" +
                "n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n      \\n     \\n       江苏19岁女孩王明霞父亲去世，家境贫困，自己努\n" +
                "力考上大学。现在为筹措学费，每天做6000只酒盒赚钱。虽然辛苦，但她说，会坚持下去。 \\n      \\n     \\n    \\n   \\n  \\n \\n \\n  \\n \\n \\n \\n  \\n   \\n   \\\n" +
                "n   \\n  \\n  \\n  \\n    政策 \\n   \\n  \\n  \\n \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       四川印发意见，将在部分高速公路开展分时段差\n" +
                "异化收费试点。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n     \\n       江西印发方案，在大型制造业企业中推广\n" +
                "卓越绩效管理标准等先进质量管理模式，计划到2022年推广率达到100%。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\n    \\n   \\n   \\n    \\n     \\n\n" +
                "     \\n       郑州出台措施支持社会力量提供医疗服务，将设立社会办医奖补资金，最高奖励1000万元。 \\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n  \\n   \\\n" +
                "n    \\n   \\n   \\n    \\n     \\n     \\n       西安警方推出史上最严整治举措，对于不听劝阻的不文明养犬者，将吊销养犬登记证，5年内不得再养犬。 \\n     \n" +
                " \\n     \\n    \\n   \\n  \\n  \\n \\n \\n \\n  \\n   \\n   \\n   \\n  \\n  \\n  \\n    生活提示 \\n   \\n  \\n  \\n \\n \\n　　今日天气：新疆、黄淮、华北、东北、华南\n" +
                "、海南岛、云南、甘肃等地有中到大雨，河南、山东、河北、辽宁、吉林等地有暴雨或大暴雨，上述局地伴有短时强降水等强对流天气。 \\n \\n \\n　　铁路：成昆铁\n" +
                "路水害线路恢复试运行，目前仅开通货运列车，客运列车的恢复开行时间待定。 \\n \\n　　北京：因降雨临时关闭的八达岭长城景区13日起恢复营业和正常游览接待\n" +
                " \\n \\n \\n  \\n   \\n    \\n     \\n      \\n     \\n     \\n     \\n       　　努力的意义，不在于一定会让你取得多大成就，而是让你在平凡的日子里，与生活少\n" +
                "一点妥协。多努力一点，也许会让你有更多力气去保护你喜欢的东西，让你与心仪的美好事物更加靠近，更重要是让你在时光的流逝中逐渐成为更好的自己。早安！ \n" +
                "\\n      \\n     \\n    \\n   \\n  \\n  \\n \\n \\n \\n本期编辑：蒋川 &nbsp;实习生：段沛昭 王如诗 | 新闻播报：蓝艳，天气主播：蓝艳 \\n \\n觉得不错，请点赞↓↓↓\n" +
                " \\n \\n \\n\",\"producttype\":\"2\",\"province\":\"20011\",\"addtime\":\"2018-08-14 07:00:40\",\"sceneid\":\"164\",\"userip\":\"\",\"srcport\":\"\",\"sendtime\":\"2019-07-26 1\n" +
                "8:28:21\"}";
        System.out.println(JSON.parseObject(infp));
    }

}
