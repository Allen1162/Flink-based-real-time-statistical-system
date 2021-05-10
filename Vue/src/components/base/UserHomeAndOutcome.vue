<template>
  <div>
    <Spin fix v-if="spinShow">
      <Icon type="ios-loading" size=36 class="spin-icon-load"/>
      <div>Loading</div>
    </Spin>
    <!-- 初始化echarts 需要有个带有宽高的盒子   -->
    <div ref="mapbox" style="height: 400px;width: 100%;margin-bottom: 16px;margin-top: 16px"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import RootVue from '../../root/RootVue';
import 'echarts/map/js/china'
import geos from '../../assets/geos'

export default {
  data() {
    return {
      spinShow: true,
      myecheats: null,
      option: {
        backgroundColor: '#032C4B',
        title: {
          text: "各城市销售总额",
          left: 'center',
          textStyle: {
            color: '#fff'
          }
        },
        tooltip: {
          trigger: 'item',
          // formatter: '{a}<br/>{b}<br/>{c}'
          formatter: '{a}<br/>{b}<br/>{c}'
        },
        legend: {
          orient: 'vertical',
          top: 'bottom',
          left: 'right',
          data: ['hot'],
          textStyle: {
            color: '#fff'
          }
        },
        visualMap: {
          min: 0,
          max: 2000,
          calculable: true,
          // color: ['yellow'],
          inRange: {
            color: [ '#FFFFE0', '#FAFAD2','#FFFF00','#FFD700']
            // color: ['yellow']
          },
          textStyle: {
            color: '#fff'
          }
        },
        geo: {
          map: 'china',
          zoom: 1.2, //当前视角的缩放比例
          // roam: true, //是否开启平游或缩放
          // scaleLimit: { //滚轮缩放的极限控制
          //   min: 1,
          //   max: 2
          // },
          itemStyle: {
            // areaColor: '#040811',
            // borderColor: '#111'
            normal: {
              color: "rgba(51, 69, 89, .5)", //地图背景色
              borderColor: "#516a89", //省市边界线00fcff 516a89
              borderWidth: 1
            }
          },
          emphasis: {
            // itemStyle: {
            //   areaColor: '#040811'
            // },
            // color: "rgba(37, 43, 61, .5)",
            label: {
              show: false
            }
          }
        },
        series: [
          {
            name: "城市支出额",
            type: 'scatter',
            // type: 'effectScatter',
            coordinateSystem: 'geo',
            // zlevel: 2,
            // rippleEffect: {
            //   //涟漪特效
            //   period: 4, //动画时间，值越小速度越快
            //   brushType: "stroke", //波纹绘制方式 stroke, fill
            //   scale: 4 //波纹圆环最大限制，值越大波纹越大
            // },
            data: null,
            // symbolSize:3,
            symbolSize: function (val) {
              return val[2] / 100;
            },
            // encode: {
            //   value: 2
            // },
            label: {
              formatter: '{b}',
              position: 'right',
              show: false
            },
            // itemStyle: {
            //   color: 'purple'
            // },
            emphasis: {
              label: {
                show: true
              }
            },
            // zlevel: 0
          },
          {
            name: 'Top 5',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            // data: convertData(data.sort(function (a, b) {
            //   return b.value - a.value;
            // }).slice(0, 6)),
            data: null,
            symbolSize: function (val) {
              return val[2] / 100;
            },
            // encode: {
            //   value: 2
            // },
            showEffectOn: 'render',
            rippleEffect: {
              brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
              formatter: '{b}',
              position: 'right',
              show: true
            },
            itemStyle: {
              // color: 'purple',
              shadowBlur: 10,
              shadowColor: '#333'
            },
            // zlevel: 1
          }
          ]
      },
    }
  },
  mounted() {
    this.myecharts = echarts.init(this.$refs.mapbox)
    //console.log(geos)
    RootVue.$on("USER:OUTCOME", message => {
      // console.log("message :::" + message)
      let array = JSON.parse(message)
      // console.log("array :" + array.userHomeAndIncomes)
      this.option.series[0].data = this.convertData(array.userHomeAndOutcomes)
      // console.log(this.option.series[0].data.length)
      this.option.series[1].data = this.convertData(array.userHomeAndOutcomes.sort(function (a,b){
        return b.userOutcome - a.userOutcome;
      }).slice(0,5))
      // console.log(this.option.series[1].data.length)
      this.myecharts.setOption(this.option)
      this.spinShow = false
    })
  },
  methods: {
    convertData(data) {
      let res = [];
      // console.log("data.length" + data.length)
      // console.log("data :" + data)
      for (let i = 0; i < data.length; i++) {
        // console.log("data[i].userHome" + data[i].userHome)
        let geoCoord = geos[data[i].userHome];
        // console.log("geoCoord" + geoCoord)
        if (geoCoord) {
          res.push({
            name: data[i].userHome,
            value: geoCoord.concat(data[i].userOutcome)
          });
        }
      }
      return res;
    }
  }
}
</script>

<style scoped>
.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
</style>