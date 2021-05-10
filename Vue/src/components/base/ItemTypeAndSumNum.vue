<template>
  <div>
    <Spin fix v-if="spinShow">
      <Icon type="ios-loading" size=18 class="spin-icon-load"/>
      <div>Loading</div>
    </Spin>
<!--    <h1 class="itemCenter">用户来源分析</h1>-->
    <h1 class="itemCenter">商品销售量占比分析</h1>
    <card class="itemCenter">
      <!-- 初始化echarts 需要有个带有宽高的盒子   -->
      <div ref="pipebox" style="height: 300px;width: 700px"></div>
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
      myecheats: null,
      option: {
        // title:{
        //     text: "用户来源分析",
        //     left: 'center'
        // },
        legend: {
          orient: 'vertical',
          left: '20%',
          // data: ['手机', '日用品', '电脑', '耳机', '平板', '跑鞋']
          data: []
        },
        tooltip: {
          trigger: 'item',
        },
        series: [{
          name: "商品销量",
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
      // option2: {
      //   legend: {},
      //   tooltip: {
      //     trigger: 'axis',
      //     showContent: false
      //   },
      //   dataset: {
      //     source: [
      //       ['product', '2012', '2013', '2014', '2015', '2016', '2017'],
      //       ['Matcha Latte', 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
      //       ['Milk Tea', 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
      //       ['Cheese Cocoa', 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
      //       ['Walnut Brownie', 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
      //     ]
      //   },
      //   xAxis: {type: 'category'},
      //   yAxis: {gridIndex: 0},
      //   grid: {top: '55%'},
      //   series: [
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {type: 'line', smooth: true, seriesLayoutBy: 'row'},
      //     {
      //       type: 'pie',
      //       id: 'pie',
      //       radius: '30%',
      //       center: ['50%', '25%'],
      //       label: {
      //         formatter: '{b}: {@2012} ({d}%)'
      //       },
      //       encode: {
      //         itemName: 'product',
      //         value: '2012',
      //         tooltip: '2012'
      //       }
      //     }
      //   ]
      // }
    }
  },
  mounted() {
    this.myecharts = echarts.init(this.$refs.pipebox)
    RootVue.$on("ITEM:TYPE:SUMNUM:SUMPRICE", message => {
      let data = JSON.parse(message)
      this.itemTypeAndSumPriceArrayList = data.itemTypeAndSumPriceArrayList
      let pie = []
      let legendData = []
      for (let i in data.itemTypeAndSumPriceArrayList) {
        legendData.push(data.itemTypeAndSumPriceArrayList[i].itemType)
        pie.push({
          value: data.itemTypeAndSumPriceArrayList[i].itemSumNum,
          name: data.itemTypeAndSumPriceArrayList[i].itemType
        })
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemType : " + data.itemTypeAndSumPriceArrayList[i].itemType)
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemSumNum : " + data.itemTypeAndSumPriceArrayList[i].itemSumNum)
      }
      // console.log("pie = " + pie)
      this.option.legend.data = legendData
      this.option.series[0].data = pie
      // console.log("this.option.series[0].data ==" + this.option.series[0].data)
      this.myecharts.setOption(this.option, true)
      this.spinShow = false
    })
    // RootVue.$on("ITEM:TYPE:SUMNUM:SUMPRICE", message => {
    //   let data = JSON.parse(message)
    // })
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

</style>
