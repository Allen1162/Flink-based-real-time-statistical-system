package scau.Allen_HJL.Test3;

public class UtilSpiltCity {

    public static void main(String[] args) {
        String s = "\"北京\": [116.407394, 39.904211],:\n" +
                "    \"天津\": [117.200983, 39.084158],:\n" +
                "    \"上海\": [121.473662, 31.230372],:\n" +
                "    \"重庆\": [106.551643, 29.562849],:\n" +
                "    \"石家庄\": [114.514793, 38.042228],:\n" +
                "    \"唐山\": [118.180193, 39.630867],:\n" +
                "    \"秦皇岛\": [119.518197, 39.888701],:\n" +
                "    \"邯郸\": [114.538959, 36.625594],:\n" +
                "    \"邢台\": [114.504677, 37.070834],:\n" +
                "    \"保定\": [115.464589, 38.874434],:\n" +
                "    \"张家口\": [114.886252, 40.768493],:\n" +
                "    \"承德\": [117.962749, 40.952942],:\n" +
                "    \"沧州\": [116.838834, 38.304477],:\n" +
                "    \"廊坊\": [116.683752, 39.538047],:\n" +
                "    \"衡水\": [115.670177, 37.73892],:\n" +
                "    \"太原\": [112.548879, 37.87059],:\n" +
                "    \"大同\": [113.300129, 40.076763],:\n" +
                "    \"阳泉\": [113.580519, 37.856971],:\n" +
                "    \"长治\": [113.116404, 36.195409],:\n" +
                "    \"晋城\": [112.851486, 35.490684],:\n" +
                "    \"朔州\": [112.432991, 39.331855],:\n" +
                "    \"晋中\": [112.752652, 37.687357],:\n" +
                "    \"运城\": [111.00746, 35.026516],:\n" +
                "    \"忻州\": [112.734174, 38.416663],:\n" +
                "    \"临汾\": [111.518975, 36.088005],:\n" +
                "    \"吕梁\": [111.144699, 37.519126],:\n" +
                "    \"沈阳\": [123.465035, 41.677284],:\n" +
                "    \"大连\": [121.614848, 38.914086],:\n" +
                "    \"鞍山\": [122.994329, 41.108647],:\n" +
                "    \"抚顺\": [123.957208, 41.880872],:\n" +
                "    \"本溪\": [123.685142, 41.486981],:\n" +
                "    \"丹东\": [124.35445, 40.000787],:\n" +
                "    \"锦州\": [121.126846, 41.095685],:\n" +
                "    \"营口\": [122.219458, 40.625364],:\n" +
                "    \"阜新\": [121.670273, 42.021602],:\n" +
                "    \"辽阳\": [123.236974, 41.267794],:\n" +
                "    \"盘锦\": [122.170584, 40.719847],:\n" +
                "    \"铁岭\": [123.726035, 42.223828],:\n" +
                "    \"朝阳\": [120.450879, 41.573762],:\n" +
                "    \"葫芦岛市\": [120.836939, 40.71104],:\n" +
                "    \"长春\": [125.323513, 43.817251],:\n" +
                "    \"吉林\": [126.549572, 43.837883],:\n" +
                "    \"四平\": [124.350398, 43.166419],:\n" +
                "    \"辽源\": [125.14366, 42.887766],:\n" +
                "    \"通化\": [125.939697, 41.728401],:\n" +
                "    \"白山\": [126.41473, 41.943972],:\n" +
                "    \"松原\": [124.825042, 45.141548],:\n" +
                "    \"白城\": [122.838714, 45.619884],:\n" +
                "    \"延边朝鲜族自治州\": [129.471868, 42.909408],:\n" +
                "    \"哈尔滨\": [126.534967, 45.803775],:\n" +
                "    \"齐齐哈尔\": [123.918186, 47.354348],:\n" +
                "    \"鸡西\": [130.969333, 45.295075],:\n" +
                "    \"鹤岗\": [130.297943, 47.350189],:\n" +
                "    \"双鸭山\": [131.141195, 46.676418],:\n" +
                "    \"大庆\": [125.103784, 46.589309],:\n" +
                "    \"伊春\": [128.841125, 47.727535],:\n" +
                "    \"佳木斯\": [130.318878, 46.799777],:\n" +
                "    \"七台河\": [131.003082, 45.771396],:\n" +
                "    \"牡丹江\": [129.633168, 44.551653],:\n" +
                "    \"黑河\": [127.528293, 50.245129],:\n" +
                "    \"绥化\": [126.968887, 46.653845],:\n" +
                "    \"大兴安岭地区\": [124.711526, 52.335262],:\n" +
                "    \"南京\": [118.796682, 32.05957],:\n" +
                "    \"无锡\": [120.31191, 31.491169],:\n" +
                "    \"徐州\": [117.284124, 34.205768],:\n" +
                "    \"常州\": [119.974061, 31.811226],:\n" +
                "    \"苏州\": [120.585728, 31.2974],:\n" +
                "    \"南通\": [120.894676, 31.981143],:\n" +
                "    \"连云港\": [119.221611, 34.596653],:\n" +
                "    \"淮安\": [119.113185, 33.551052],:\n" +
                "    \"盐城\": [120.163107, 33.347708],:\n" +
                "    \"扬州\": [119.412939, 32.394209],:\n" +
                "    \"镇江\": [119.425836, 32.187849],:\n" +
                "    \"泰州\": [119.922933, 32.455536],:\n" +
                "    \"宿迁\": [118.275198, 33.963232],:\n" +
                "    \"杭州\": [120.209789, 30.24692],:\n" +
                "    \"宁波\": [121.622485, 29.859971],:\n" +
                "    \"温州\": [120.699361, 27.993828],:\n" +
                "    \"嘉兴\": [120.75547, 30.746191],:\n" +
                "    \"湖州\": [120.086809, 30.89441],:\n" +
                "    \"绍兴\": [120.580364, 30.030192],:\n" +
                "    \"金华\": [119.647229, 29.079208],:\n" +
                "    \"衢州\": [118.859457, 28.970079],:\n" +
                "    \"舟山\": [122.207106, 29.985553],:\n" +
                "    \"台州\": [121.42076, 28.65638],:\n" +
                "    \"丽水\": [119.922796, 28.46763],:\n" +
                "    \"合肥\": [117.227219, 31.820591],:\n" +
                "    \"芜湖\": [118.432941, 31.352859],:\n" +
                "    \"蚌埠\": [117.388512, 32.91663],:\n" +
                "    \"淮南\": [117.018399, 32.587117],:\n" +
                "    \"马鞍山\": [118.507011, 31.67044],:\n" +
                "    \"淮北\": [116.798265, 33.955844],:\n" +
                "    \"铜陵\": [117.81154, 30.945515],:\n" +
                "    \"安庆\": [117.115101, 30.531919],:\n" +
                "    \"黄山\": [118.338272, 29.715185],:\n" +
                "    \"滁州\": [118.327944, 32.255636],:\n" +
                "    \"阜阳\": [115.814504, 32.890479],:\n" +
                "    \"宿州\": [116.964195, 33.647309],:\n" +
                "    \"六安\": [116.520139, 31.735456],:\n" +
                "    \"亳州\": [115.77867, 33.844592],:\n" +
                "    \"池州\": [117.491592, 30.664779],:\n" +
                "    \"宣城\": [118.75868, 30.940195],:\n" +
                "    \"福州\": [119.296389, 26.074268],:\n" +
                "    \"厦门\": [118.089204, 24.479664],:\n" +
                "    \"莆田\": [119.007777, 25.454084],:\n" +
                "    \"三明\": [117.638678, 26.263406],:\n" +
                "    \"泉州\": [118.675676, 24.874132],:\n" +
                "    \"漳州\": [117.647093, 24.513025],:\n" +
                "    \"南平\": [118.17771, 26.641774],:\n" +
                "    \"龙岩\": [117.017295, 25.075119],:\n" +
                "    \"宁德\": [119.547932, 26.665617],:\n" +
                "    \"南昌\": [115.858198, 28.682892],:\n" +
                "    \"景德镇\": [117.178222, 29.268945],:\n" +
                "    \"萍乡\": [113.887083, 27.658373],:\n" +
                "    \"九江\": [115.952914, 29.662117],:\n" +
                "    \"新余\": [114.917346, 27.817808],:\n" +
                "    \"鹰潭\": [117.042173, 28.272537],:\n" +
                "    \"赣州\": [114.933546, 25.830694],:\n" +
                "    \"吉安\": [114.966567, 27.090763],:\n" +
                "    \"宜春\": [114.416785, 27.815743],:\n" +
                "    \"抚州\": [116.358181, 27.949217],:\n" +
                "    \"上饶\": [117.943433, 28.454863],:\n" +
                "    \"济南\": [117.120098, 36.6512],:\n" +
                "    \"青岛\": [120.382621, 36.067131],:\n" +
                "    \"淄博\": [118.055019, 36.813546],:\n" +
                "    \"枣庄\": [117.323725, 34.810488],:\n" +
                "    \"东营\": [118.674614, 37.433963],:\n" +
                "    \"烟台\": [121.447852, 37.464539],:\n" +
                "    \"潍坊\": [119.161748, 36.706962],:\n" +
                "    \"济宁\": [116.587282, 35.414982],:\n" +
                "    \"泰安\": [117.087614, 36.200252],:\n" +
                "    \"威海\": [122.120282, 37.513412],:\n" +
                "    \"日照\": [119.526925, 35.416734],:\n" +
                "    \"莱芜\": [117.676723, 36.213813],:\n" +
                "    \"临沂\": [118.356414, 35.104673],:\n" +
                "    \"德州\": [116.359381, 37.436657],:\n" +
                "    \"聊城\": [115.985389, 36.456684],:\n" +
                "    \"滨州\": [117.970699, 37.38198],:\n" +
                "    \"菏泽\": [115.480656, 35.23375],:\n" +
                "    \"郑州\": [113.625328, 34.746611],:\n" +
                "    \"开封\": [114.307677, 34.797966],:\n" +
                "    \"洛阳\": [112.453926, 34.620202],:\n" +
                "    \"平顶山\": [113.192661, 33.766169],:\n" +
                "    \"安阳\": [114.392392, 36.097577],:\n" +
                "    \"鹤壁\": [114.297309, 35.748325],:\n" +
                "    \"新乡\": [113.926763, 35.303704],:\n" +
                "    \"焦作\": [113.241823, 35.215893],:\n" +
                "    \"濮阳\": [115.029216, 35.761829],:\n" +
                "    \"许昌\": [113.852454, 34.035771],:\n" +
                "    \"漯河\": [114.016536, 33.580873],:\n" +
                "    \"三门峡\": [111.200367, 34.772792],:\n" +
                "    \"南阳\": [112.528308, 32.990664],:\n" +
                "    \"商丘\": [115.656339, 34.414961],:\n" +
                "    \"信阳\": [114.091193, 32.147679],:\n" +
                "    \"周口\": [114.69695, 33.626149],:\n" +
                "    \"驻马店\": [114.022247, 33.012885],:\n" +
                "    \"济源\": [112.602256, 35.067199],:\n" +
                "    \"武汉\": [114.305469, 30.593175],:\n" +
                "    \"黄石\": [115.038962, 30.201038],:\n" +
                "    \"十堰\": [110.799291, 32.629462],:\n" +
                "    \"宜昌\": [111.286445, 30.691865],:\n" +
                "    \"襄阳\": [112.122426, 32.009016],:\n" +
                "    \"鄂州\": [114.894935, 30.391141],:\n" +
                "    \"荆门\": [112.199427, 31.035395],:\n" +
                "    \"孝感\": [113.957037, 30.917766],:\n" +
                "    \"荆州\": [112.239746, 30.335184],:\n" +
                "    \"黄冈\": [114.872199, 30.453667],:\n" +
                "    \"咸宁\": [114.322616, 29.841362],:\n" +
                "    \"随州\": [113.382515, 31.690191],:\n" +
                "    \"恩施土家族苗族自治州\": [109.488172, 30.272156],:\n" +
                "    \"潜江\": [112.899762, 30.402167],:\n" +
                "    \"神农架林区\": [110.675743, 31.744915],:\n" +
                "    \"天门\": [113.166078, 30.663337],:\n" +
                "    \"仙桃\": [113.423583, 30.361438],:\n" +
                "    \"长沙\": [112.938884, 28.22808],:\n" +
                "    \"株洲\": [113.133853, 27.827986],:\n" +
                "    \"湘潭\": [112.944026, 27.829795],:\n" +
                "    \"衡阳\": [112.572018, 26.893368],:\n" +
                "    \"邵阳\": [111.467674, 27.23895],:\n" +
                "    \"岳阳\": [113.12873, 29.356803],:\n" +
                "    \"常德\": [111.698784, 29.031654],:\n" +
                "    \"张家界\": [110.479148, 29.117013],:\n" +
                "    \"益阳\": [112.355129, 28.554349],:\n" +
                "    \"郴州\": [113.014984, 25.770532],:\n" +
                "    \"永州\": [111.613418, 26.419641],:\n" +
                "    \"怀化\": [110.001923, 27.569517],:\n" +
                "    \"娄底\": [111.994482, 27.70027],:\n" +
                "    \"湘西土家族苗族自治州\": [109.738906, 28.31195],:\n" +
                "    \"广州\": [113.264385, 23.12911],:\n" +
                "    \"韶关\": [113.59762, 24.810879],:\n" +
                "    \"深圳\": [114.057939, 22.543527],:\n" +
                "    \"珠海\": [113.576677, 22.270978],:\n" +
                "    \"汕头\": [116.681972, 23.354091],:\n" +
                "    \"佛山\": [113.121435, 23.021478],:\n" +
                "    \"江门\": [113.081542, 22.57899],:\n" +
                "    \"湛江\": [110.356639, 21.270145],:\n" +
                "    \"茂名\": [110.925439, 21.662991],:\n" +
                "    \"肇庆\": [112.465091, 23.047191],:\n" +
                "    \"惠州\": [114.415612, 23.112381],:\n" +
                "    \"梅州\": [116.122523, 24.288578],:\n" +
                "    \"汕尾\": [115.375431, 22.78705],:\n" +
                "    \"河源\": [114.700961, 23.743686],:\n" +
                "    \"阳江\": [111.982589, 21.857887],:\n" +
                "    \"清远\": [113.056042, 23.681774],:\n" +
                "    \"东莞\": [113.751799, 23.020673],:\n" +
                "    \"中山\": [113.39277, 22.517585],:\n" +
                "    \"潮州\": [116.622444, 23.657262],:\n" +
                "    \"揭阳\": [116.372708, 23.549701],:\n" +
                "    \"云浮\": [112.044491, 22.915094],:\n" +
                "    \"东沙群岛\": [116.887613, 20.617825],:\n" +
                "    \"白沙黎族自治县\": [109.451484, 19.224823],:\n" +
                "    \"保亭黎族苗族自治县\": [109.70259, 18.63913],:\n" +
                "    \"昌江黎族自治县\": [109.055739, 19.298184],:\n" +
                "    \"澄迈县\": [110.006754, 19.738521],:\n" +
                "    \"海口\": [110.198286, 20.044412],:\n" +
                "    \"三亚\": [109.511772, 18.253135],:\n" +
                "    \"三沙\": [112.338695, 16.831839],:\n" +
                "    \"儋州\": [109.580811, 19.521134],:\n" +
                "    \"定安县\": [110.359339, 19.681404],:\n" +
                "    \"东方\": [108.651815, 19.095351],:\n" +
                "    \"乐东黎族自治县\": [109.173054, 18.750259],:\n" +
                "    \"临高县\": [109.690508, 19.912025],:\n" +
                "    \"陵水黎族自治县\": [110.037503, 18.506048],:\n" +
                "    \"琼海\": [110.474497, 19.259134],:\n" +
                "    \"琼中黎族苗族自治县\": [109.838389, 19.033369],:\n" +
                "    \"屯昌县\": [110.103415, 19.351765],:\n" +
                "    \"万宁\": [110.391073, 18.795143],:\n" +
                "    \"文昌\": [110.797717, 19.543422],:\n" +
                "    \"五指山\": [109.516925, 18.775146],:\n" +
                "    \"成都\": [104.066794, 30.572893],:\n" +
                "    \"自贡\": [104.778442, 29.33903],:\n" +
                "    \"攀枝花\": [101.718637, 26.582347],:\n" +
                "    \"泸州\": [105.442285, 28.871805],:\n" +
                "    \"德阳\": [104.397894, 31.126855],:\n" +
                "    \"绵阳\": [104.679004, 31.467459],:\n" +
                "    \"广元\": [105.843357, 32.435435],:\n" +
                "    \"遂宁\": [105.592803, 30.53292],:\n" +
                "    \"内江\": [105.058432, 29.580228],:\n" +
                "    \"乐山\": [103.765678, 29.552115],:\n" +
                "    \"南充\": [106.110698, 30.837793],:\n" +
                "    \"眉山\": [103.848403, 30.076994],:\n" +
                "    \"宜宾\": [104.642845, 28.752134],:\n" +
                "    \"广安\": [106.633088, 30.456224],:\n" +
                "    \"达州\": [107.467758, 31.209121],:\n" +
                "    \"雅安\": [103.042375, 30.010602],:\n" +
                "    \"巴中\": [106.747477, 31.867903],:\n" +
                "    \"资阳\": [104.627636, 30.128901],:\n" +
                "    \"阿坝藏族羌族自治州\": [102.224653, 31.899413],:\n" +
                "    \"甘孜藏族自治州\": [101.96231, 30.04952],:\n" +
                "    \"凉山彝族自治州\": [102.267712, 27.88157],:\n" +
                "    \"贵阳\": [106.630153, 26.647661],:\n" +
                "    \"六盘水\": [104.830458, 26.592707],:\n" +
                "    \"遵义\": [106.927389, 27.725654],:\n" +
                "    \"安顺\": [105.947594, 26.253088],:\n" +
                "    \"毕节\": [105.291702, 27.283908],:\n" +
                "    \"铜仁\": [109.189598, 27.731514],:\n" +
                "    \"黔西南布依族苗族自治州\": [104.906397, 25.087856],:\n" +
                "    \"黔东南苗族侗族自治州\": [107.982874, 26.583457],:\n" +
                "    \"黔南布依族苗族自治州\": [107.522171, 26.253275],:\n" +
                "    \"昆明\": [102.832891, 24.880095],:\n" +
                "    \"曲靖\": [103.796167, 25.489999],:\n" +
                "    \"玉溪\": [102.527197, 24.347324],:\n" +
                "    \"保山\": [99.161761, 25.112046],:\n" +
                "    \"昭通\": [103.717465, 27.338257],:\n" +
                "    \"丽江\": [100.22775, 26.855047],:\n" +
                "    \"普洱\": [100.966156, 22.825155],:\n" +
                "    \"临沧\": [100.08879, 23.883955],:\n" +
                "    \"楚雄彝族自治州\": [101.527992, 25.045513],:\n" +
                "    \"红河哈尼族彝族自治州\": [103.374893, 23.363245],:\n" +
                "    \"文山壮族苗族自治州\": [104.216248, 23.400733],:\n" +
                "    \"西双版纳傣族自治州\": [100.796984, 22.009113],:\n" +
                "    \"大理白族自治州\": [100.267638, 25.606486],:\n" +
                "    \"德宏傣族景颇族自治州\": [98.584895, 24.433353],:\n" +
                "    \"怒江傈僳族自治州\": [98.8566, 25.817555],:\n" +
                "    \"迪庆藏族自治州\": [99.702583, 27.818807],:\n" +
                "    \"西安\": [108.93977, 34.341574],:\n" +
                "    \"铜川\": [108.945019, 34.897887],:\n" +
                "    \"宝鸡\": [107.237743, 34.363184],:\n" +
                "    \"咸阳\": [108.709136, 34.32987],:\n" +
                "    \"渭南\": [109.471094, 34.52044],:\n" +
                "    \"延安\": [109.494112, 36.651381],:\n" +
                "    \"汉中\": [107.02305, 33.067225],:\n" +
                "    \"榆林\": [109.734474, 38.285369],:\n" +
                "    \"安康\": [109.029113, 32.68481],:\n" +
                "    \"商洛\": [109.91857, 33.872726],:\n" +
                "    \"兰州\": [103.834303, 36.061089],:\n" +
                "    \"嘉峪关\": [98.289419, 39.772554],:\n" +
                "    \"金昌\": [102.188117, 38.520717],:\n" +
                "    \"白银\": [104.138771, 36.545261],:\n" +
                "    \"天水\": [105.724979, 34.580885],:\n" +
                "    \"武威\": [102.638201, 37.928267],:\n" +
                "    \"张掖\": [100.449913, 38.925548],:\n" +
                "    \"平凉\": [106.665061, 35.542606],:\n" +
                "    \"酒泉\": [98.493927, 39.732795],:\n" +
                "    \"庆阳\": [107.643571, 35.70898],:\n" +
                "    \"定西\": [104.592225, 35.606978],:\n" +
                "    \"陇南\": [104.960851, 33.37068],:\n" +
                "    \"临夏回族自治州\": [103.210655, 35.601352],:\n" +
                "    \"甘南藏族自治州\": [102.910995, 34.983409],:\n" +
                "    \"西宁\": [101.778223, 36.617134],:\n" +
                "    \"海东\": [102.104287, 36.502039],:\n" +
                "    \"海北藏族自治州\": [100.900997, 36.954413],:\n" +
                "    \"黄南藏族自治州\": [102.015248, 35.519548],:\n" +
                "    \"海南藏族自治州\": [100.622692, 36.296529],:\n" +
                "    \"果洛藏族自治州\": [100.244808, 34.471431],:\n" +
                "    \"玉树藏族自治州\": [97.091934, 33.011674],:\n" +
                "    \"海西蒙古族藏族自治州\": [97.369751, 37.377139],:\n" +
                "    \"台湾省\": [121.509062, 25.044332],:\n" +
                "    \"呼和浩特\": [111.749995, 40.842356],:\n" +
                "    \"包头\": [109.953504, 40.621157],:\n" +
                "    \"乌海\": [106.794216, 39.655248],:\n" +
                "    \"赤峰\": [118.88694, 42.257843],:\n" +
                "    \"通辽\": [122.243444, 43.652889],:\n" +
                "    \"鄂尔多斯\": [109.781327, 39.608266],:\n" +
                "    \"呼伦贝尔\": [119.765558, 49.211576],:\n" +
                "    \"巴彦淖尔\": [107.387657, 40.743213],:\n" +
                "    \"乌兰察布\": [113.132584, 40.994785],:\n" +
                "    \"兴安盟\": [122.037657, 46.082462],:\n" +
                "    \"锡林郭勒盟\": [116.048222, 43.933454],:\n" +
                "    \"阿拉善盟\": [105.728957, 38.851921],:\n" +
                "    \"南宁\": [108.366543, 22.817002],:\n" +
                "    \"柳州\": [109.428608, 24.326291],:\n" +
                "    \"桂林\": [110.179953, 25.234479],:\n" +
                "    \"梧州\": [111.279115, 23.476962],:\n" +
                "    \"北海\": [109.120161, 21.481291],:\n" +
                "    \"防城港\": [108.353846, 21.68686],:\n" +
                "    \"钦州\": [108.654146, 21.979933],:\n" +
                "    \"贵港\": [109.598926, 23.11153],:\n" +
                "    \"玉林\": [110.18122, 22.654032],:\n" +
                "    \"百色\": [106.618202, 23.90233],:\n" +
                "    \"贺州\": [111.566871, 24.403528],:\n" +
                "    \"河池\": [108.085261, 24.692931],:\n" +
                "    \"来宾\": [109.221465, 23.750306],:\n" +
                "    \"崇左\": [107.365094, 22.377253],:\n" +
                "    \"拉萨\": [91.172148, 29.652341],:\n" +
                "    \"日喀则\": [88.880583, 29.266869],:\n" +
                "    \"昌都\": [97.17202, 31.140969],:\n" +
                "    \"林芝\": [94.36149, 29.649128],:\n" +
                "    \"山南\": [91.773134, 29.237137],:\n" +
                "    \"那曲区\": [92.052064, 31.476479],:\n" +
                "    \"阿里地区\": [80.105804, 32.501111],:\n" +
                "    \"银川\": [106.230909, 38.487193],:\n" +
                "    \"石嘴山\": [106.383303, 38.983236],:\n" +
                "    \"吴忠\": [106.198913, 37.997428],:\n" +
                "    \"固原\": [106.24261, 36.015855],:\n" +
                "    \"中卫\": [105.196902, 37.499972],:\n" +
                "    \"阿拉尔\": [81.280527, 40.547653],:\n" +
                "    \"北屯\": [87.837075, 47.332643],:\n" +
                "    \"可克达拉\": [81.044542, 43.944798],:\n" +
                "    \"昆玉\": [79.291083, 37.209642],:\n" +
                "    \"石河子\": [86.080602, 44.306097],:\n" +
                "    \"双河市\": [82.353656, 44.840524],:\n" +
                "    \"乌鲁木齐\": [87.616848, 43.825592],:\n" +
                "    \"克拉玛依\": [84.889207, 45.579888],:\n" +
                "    \"吐鲁番\": [89.189752, 42.951303],:\n" +
                "    \"哈密\": [93.515224, 42.819541],:\n" +
                "    \"昌吉回族自治州\": [87.308224, 44.011182],:\n" +
                "    \"博尔塔拉蒙古自治州\": [82.066363, 44.906039],:\n" +
                "    \"巴音郭楞蒙古自治州\": [86.145297, 41.764115],:\n" +
                "    \"阿克苏地区\": [80.260605, 41.168779],:\n" +
                "    \"克孜勒苏柯尔克孜自治州\": [76.167819, 39.714526],:\n" +
                "    \"喀什地区\": [75.989741, 39.47046],:\n" +
                "    \"和田地区\": [79.922211, 37.114157],:\n" +
                "    \"伊犁哈萨克自治州\": [81.324136, 43.916823],:\n" +
                "    \"塔城地区\": [82.980316, 46.745364],:\n" +
                "    \"阿勒泰地区\": [88.141253, 47.844924],:\n" +
                "    \"铁门关\": [85.501217, 41.82725],:\n" +
                "    \"图木舒克\": [79.073963, 39.868965],:\n" +
                "    \"五家渠\": [87.54324, 44.166756],:\n" +
                "    \"香港特别行政区\": [114.171203, 22.277468],:\n" +
                "    \"澳门特别行政区\": [113.543028, 22.186835]";
        String[] city = s.split(":");
//        for(int i=0; i<city.length; i++){
//            if(i%2==0)
//            System.out.println(city[i]);
//        }

        String s1 = "\"北京\" \"天津\" \"上海\" \"重庆\" \"石家庄\" \"唐山\" \"秦皇岛\" \"邯郸\" \"邢台\" \"保定\" \"张家口\" \"承德\" \"沧州\" \"廊坊\" \"衡水\" \"太原\" \"大同\" \"阳泉\" \"长治\" \"晋城\" \"朔州\" \"晋中\" \"运城\" \"忻州\" \"临汾\" \"吕梁\" \"沈阳\" \"大连\" \"鞍山\" \"抚顺\" \"本溪\" \"丹东\" \"锦州\" \"营口\" \"阜新\" \"辽阳\" \"盘锦\" \"铁岭\" \"朝阳\" \"葫芦岛市\" \"长春\" \"吉林\" \"四平\" \"辽源\" \"通化\" \"白山\" \"松原\" \"白城\" \"延边朝鲜族自治州\" \"哈尔滨\" \"齐齐哈尔\" \"鸡西\" \"鹤岗\" \"双鸭山\" \"大庆\" \"伊春\" \"佳木斯\" \"七台河\" \"牡丹江\" \"黑河\" \"绥化\" \"大兴安岭地区\" \"南京\" \"无锡\" \"徐州\" \"常州\" \"苏州\" \"南通\" \"连云港\" \"淮安\" \"盐城\" \"扬州\" \"镇江\" \"泰州\" \"宿迁\" \"杭州\" \"宁波\" \"温州\" \"嘉兴\" \"湖州\" \"绍兴\" \"金华\" \"衢州\" \"舟山\" \"台州\" \"丽水\" \"合肥\" \"芜湖\" \"蚌埠\" \"淮南\" \"马鞍山\" \"淮北\" \"铜陵\" \"安庆\" \"黄山\" \"滁州\" \"阜阳\" \"宿州\" \"六安\" \"亳州\" \"池州\" \"宣城\" \"福州\" \"厦门\" \"莆田\" \"三明\" \"泉州\" \"漳州\" \"南平\" \"龙岩\" \"宁德\" \"南昌\" \"景德镇\" \"萍乡\" \"九江\" \"新余\" \"鹰潭\" \"赣州\" \"吉安\" \"宜春\" \"抚州\" \"上饶\" \"济南\" \"青岛\" \"淄博\" \"枣庄\" \"东营\" \"烟台\" \"潍坊\" \"济宁\" \"泰安\" \"威海\" \"日照\" \"莱芜\" \"临沂\" \"德州\" \"聊城\" \"滨州\" \"菏泽\" \"郑州\" \"开封\" \"洛阳\" \"平顶山\" \"安阳\" \"鹤壁\" \"新乡\" \"焦作\" \"濮阳\" \"许昌\" \"漯河\" \"三门峡\" \"南阳\" \"商丘\" \"信阳\" \"周口\" \"驻马店\" \"济源\" \"武汉\" \"黄石\" \"十堰\" \"宜昌\" \"襄阳\" \"鄂州\" \"荆门\" \"孝感\" \"荆州\" \"黄冈\" \"咸宁\" \"随州\" \"恩施土家族苗族自治州\" \"潜江\" \"神农架林区\" \"天门\" \"仙桃\" \"长沙\" \"株洲\" \"湘潭\" \"衡阳\" \"邵阳\" \"岳阳\" \"常德\" \"张家界\" \"益阳\" \"郴州\" \"永州\" \"怀化\" \"娄底\" \"湘西土家族苗族自治州\" \"广州\" \"韶关\" \"深圳\" \"珠海\" \"汕头\" \"佛山\" \"江门\" \"湛江\" \"茂名\" \"肇庆\" \"惠州\" \"梅州\" \"汕尾\" \"河源\" \"阳江\" \"清远\" \"东莞\" \"中山\" \"潮州\" \"揭阳\" \"云浮\" \"东沙群岛\" \"白沙黎族自治县\" \"保亭黎族苗族自治县\" \"昌江黎族自治县\" \"澄迈县\" \"海口\" \"三亚\" \"三沙\" \"儋州\" \"定安县\" \"东方\" \"乐东黎族自治县\" \"临高县\" \"陵水黎族自治县\" \"琼海\" \"琼中黎族苗族自治县\" \"屯昌县\" \"万宁\" \"文昌\" \"五指山\" \"成都\" \"自贡\" \"攀枝花\" \"泸州\" \"德阳\" \"绵阳\" \"广元\" \"遂宁\" \"内江\" \"乐山\" \"南充\" \"眉山\" \"宜宾\" \"广安\" \"达州\" \"雅安\" \"巴中\" \"资阳\" \"阿坝藏族羌族自治州\" \"甘孜藏族自治州\" \"凉山彝族自治州\" \"贵阳\" \"六盘水\" \"遵义\" \"安顺\" \"毕节\" \"铜仁\" \"黔西南布依族苗族自治州\" \"黔东南苗族侗族自治州\" \"黔南布依族苗族自治州\" \"昆明\" \"曲靖\" \"玉溪\" \"保山\" \"昭通\" \"丽江\" \"普洱\" \"临沧\" \"楚雄彝族自治州\" \"红河哈尼族彝族自治州\" \"文山壮族苗族自治州\" \"西双版纳傣族自治州\" \"大理白族自治州\" \"德宏傣族景颇族自治州\" \"怒江傈僳族自治州\" \"迪庆藏族自治州\" \"西安\" \"铜川\" \"宝鸡\" \"咸阳\" \"渭南\" \"延安\" \"汉中\" \"榆林\" \"安康\" \"商洛\" \"兰州\" \"嘉峪关\" \"金昌\" \"白银\" \"天水\" \"武威\" \"张掖\" \"平凉\" \"酒泉\" \"庆阳\" \"定西\" \"陇南\" \"临夏回族自治州\" \"甘南藏族自治州\" \"西宁\" \"海东\" \"海北藏族自治州\" \"黄南藏族自治州\" \"海南藏族自治州\" \"果洛藏族自治州\" \"玉树藏族自治州\" \"海西蒙古族藏族自治州\" \"台湾省\" \"呼和浩特\" \"包头\" \"乌海\" \"赤峰\" \"通辽\" \"鄂尔多斯\" \"呼伦贝尔\" \"巴彦淖尔\" \"乌兰察布\" \"兴安盟\" \"锡林郭勒盟\" \"阿拉善盟\" \"南宁\" \"柳州\" \"桂林\" \"梧州\" \"北海\" \"防城港\" \"钦州\" \"贵港\" \"玉林\" \"百色\" \"贺州\" \"河池\" \"来宾\" \"崇左\" \"拉萨\" \"日喀则\" \"昌都\" \"林芝\" \"山南\" \"那曲区\" \"阿里地区\" \"银川\" \"石嘴山\" \"吴忠\" \"固原\" \"中卫\" \"阿拉尔\" \"北屯\" \"可克达拉\" \"昆玉\" \"石河子\" \"双河市\" \"乌鲁木齐\" \"克拉玛依\" \"吐鲁番\" \"哈密\" \"昌吉回族自治州\" \"博尔塔拉蒙古自治州\" \"巴音郭楞蒙古自治州\" \"阿克苏地区\" \"克孜勒苏柯尔克孜自治州\" \"喀什地区\" \"和田地区\" \"伊犁哈萨克自治州\" \"塔城地区\" \"阿勒泰地区\" \"铁门关\" \"图木舒克\" \"五家渠\" \"香港特别行政区\" \"澳门特别行政区\"" ;
        String[] city1 = s1.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"北京\"");
        for(int i =1; i<city1.length; i++){
//            System.out.println(city1[i]);
            stringBuilder.append(","+city1[i]);
        }
        System.out.println(stringBuilder.toString());
    }

}
