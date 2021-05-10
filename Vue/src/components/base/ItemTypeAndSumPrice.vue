<template>
  <div>
    <Spin fix v-if="spinShow">
      <Icon type="ios-loading" size=18 class="spin-icon-load"/>
      <div>Loading</div>
    </Spin>
    <!--    <h1 class="itemCenter">用户来源分析</h1>-->
    <h1 class="itemCenter">商品销售额占比分析</h1>
    <card class="itemCenter">
      <!-- 初始化echarts 需要有个带有宽高的盒子   -->
      <div ref="pipeboxofprice" style="height: 300px;width: 700px"></div>
      <!--      <div class="itemCenter">-->
      <!--        &lt;!&ndash;        总用户数：&ndash;&gt;-->
      <!--        &lt;!&ndash;        {{allUserNum}}人&ndash;&gt;-->
      <!--      </div>-->
    </card>
    <!--    <h1 class="itemCenter">商品销售量占比分析</h1>-->
    <!--    <Row :gutter="16">-->
    <!--      <Col span="8" v-for="(item,index) in itemTypeAndSumPriceArrayList" :key="index">-->
    <!--        <card class="itemCenter">-->
    <!--          <i-circle-->
    <!--              dashboard-->
    <!--              :size="250"-->
    <!--              :trail-width="4"-->
    <!--              :stroke-width="5"-->
    <!--              :percent=item.itemSumNum-->
    <!--              stroke-linecap="square"-->
    <!--              stroke-color="#43a3fb">-->
    <!--            <div class="demo-Circle-custom">-->
    <!--              <h1>{{ item.itemSumNum }}</h1>-->
    <!--              <p>{{ item.itemType }}用户规模</p>-->
    <!--              <span>-->
    <!--                            活跃用户占比<br/>-->
    <!--                            <i><animated-number-->
    <!--                                :value=item.itemSumNum-->
    <!--                                :formatValue="doubleFormatter"-->
    <!--                                :duration="1000"-->
    <!--                            /></i>-->
    <!--                        </span>-->
    <!--            </div>-->
    <!--          </i-circle>-->
    <!--        </card>-->
    <!--      </Col>-->
    <!--    </Row>-->
  </div>
</template>

<script>
import RootVue from '../../root/RootVue'
import echarts from 'echarts'
// import AnimatedNumber from "animated-number-vue"

export default {
  components: {
    // AnimatedNumber,
  },
  data() {
    return {
      spinShow: true,
      itemTypeAndSumPriceArrayList: [],
      mydata: {
        itemTypeAndSumPriceArrayList: [{
          itemType:"",
          itemSumNum:0,
          itemSumPrice:0,
          windowEnd: 0
        }],
      },
      // legendData: [],
      myecheats: null,
      option: {
        // title:{
        //     text: "用户来源分析",
        //     left: 'center'
        // },
        legend: {
          orient: 'vertical',
          left: '20%',
          data: []
        },
        tooltip: {
          trigger: 'item',
        },
        series: [{
          name: "商品销售额",
          type: 'pie',
          radius: '55%',
          label: {
            formatter: '{b}: {d}%'
          },
          center: ['50%', '50%'],
          data: null,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      },
    }
  },
  mounted() {
    this.myecharts = echarts.init(this.$refs.pipeboxofprice)
    RootVue.$on("ITEM:TYPE:SUMNUM:SUMPRICE", message => {
      let data = JSON.parse(message)
      this.itemTypeAndSumPriceArrayList = data.itemTypeAndSumPriceArrayList
      let pie = []
      for (let i in data.itemTypeAndSumPriceArrayList) {
        this.option.legend.data.push(data.itemTypeAndSumPriceArrayList[i].itemType)
        pie.push({
          value: data.itemTypeAndSumPriceArrayList[i].itemSumPrice,
          name: data.itemTypeAndSumPriceArrayList[i].itemType
        })
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemType : " + data.itemTypeAndSumPriceArrayList[i].itemType)
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemSumNum : " + data.itemTypeAndSumPriceArrayList[i].itemSumNum)
      }
      // console.log("pie = " + pie)
      this.option.series[0].data = pie
      // console.log("this.option.series[0].data ==" + this.option.series[0].data)
      this.myecharts.setOption(this.option, true)
      this.spinShow = false
    })
  },
  methods: {
    formatter(value) {
      return Math.floor(value);
    },
    // doubleFormatter(value) {
    //   return value.toFixed(2) + '%'
    // }
  }
}
</script>

<style scoped>
.itemCenter {
  display: flex;
  justify-content: center;
  margin-top: 16px;
  margin-bottom: 16px;
}

.demo-Circle-custom h1 {
  color: #3f414d;
  font-size: 28px;
  font-weight: normal;
}

.demo-Circle-custom p {
  color: #657180;
  font-size: 14px;
  margin: 10px 0 15px;
}

.demo-Circle-custom span {
  display: block;
  padding-top: 15px;
  color: #657180;
  font-size: 14px;

}

.demo-Circle-custom span:before {
  content: '';
  display: block;
  width: 50px;
  height: 1px;
  margin: 0 auto;
  background: #e0e3e6;
  position: relative;
  top: -15px;
}

.demo-Circle-custom span i {
  font-style: normal;
  color: #3f414d;
}

.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
</style>
