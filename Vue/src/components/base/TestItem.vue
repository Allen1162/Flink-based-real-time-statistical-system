<template>
  <div>
    <Spin fix v-if="spinShow">
      <Icon type="ios-loading" size=18 class="spin-icon-load"/>
      <div>Loading</div>
    </Spin>
    <!-- 初始化echarts 需要有个带有宽高的盒子   -->
    <div ref="messagelinebox" style="height: 600px;width: 700px"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
import RootVue from '../../root/RootVue'
// import global from "@/assets/global";
export default {

  data(){
    return{
      spinShow:true,
      myecheats: null,
      // modTime:0,
      // itemTypeAndSumPriceArrayList: [],
      // mydata: {
      //   itemTypeAndSumPriceArrayList: [{
      //     itemType:"",
      //     itemSumNum:0,
      //     itemSumPrice:0,
      //     windowEnd: 0
      //   }],
      //   windowsEnd: ""
      // },

      option: {
        title:{
          text: "最近一分钟内商品各种类的销售量统计（每隔5s更新一次）",
          left: 'center'
        },
        legend:{
          right: '10%',
          orient: 'vertical',
          // data:[]
        },
        tooltip: {
          trigger: 'axis',
          // formatter: '{b} {c}',
          axisPointer: {
            animation: false
          }
        },
        grid: {
          left: '13%',
        },
        xAxis: {
          type: 'time',
          splitLine: {
            show: false
          },
          maxInterval: 5000,
          // interval: 5000
          // data: []
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '50%'],
          splitLine: {
            show: false
          },
          // scale: true
        },
        // series: [{
        //   name:"",
        //   type:'line',
        //   data:[]
        // }],
        // series:[]
        series: [{
          name: "手机",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "日用品",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "电脑",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "平板",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "耳机",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "跑鞋",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "零食",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        },{
          name: "电脑配件",
          type: 'line',
          smooth:true,
          showSymbol: false,
          hoverAnimation: false,
          // areaStyle: {
          //     normal: {}
          // },
          data: null,
        }]
      },
    }
  },
  mounted() {
    this.myecharts = echarts.init(this.$refs.messagelinebox)
    RootVue.$on("HISTORY:ITEM", message =>{
      let data = JSON.parse(message)
      // let itemType = []
      // let typeData = []
      // let timeSZ=[]
      // let pie = []
      // this.itemTypeAndSumPriceArrayList = data.itemTypeAndSumPriceArrayList
      let tmp={'手机':[],'日用品':[],'电脑':[],'平板':[],'耳机':[],'跑鞋':[],'零食':[],'电脑配件':[]}
      for (let i=0; i<data.historyItems.length; i++) {
          for(let j=0; j<data.historyItems[i].historyItemTypeAndSumPriceArrayList.length; j++){
              tmp[data.historyItems[i].historyItemTypeAndSumPriceArrayList[j].itemType].push([data.historyItems[i].windowEnd, data.historyItems[i].historyItemTypeAndSumPriceArrayList[j].itemSumNum])
          }
        // let thisType = data.itemTypeAndSumPriceArrayList[i].itemType
        // let seriesLength = this.option.series.length
        // let flag=0
        // let pie = []
        // for(let j=0; j<seriesLength; j++){
        //   if (thisType == this.option.series[j].name){
        //     // this.option.series[j].data.push(data.windowsEnd, data.itemTypeAndSumPriceArrayList[i].itemSumNum)
        //     flag=1
        //     this.option.series[j].data.push(/*data.windowsEnd,*/ data.itemTypeAndSumPriceArrayList[i].itemSumNum)
        //     break
        //   }else{
        //     continue
        //   }
        // }
        // if( flag == 0){
        //   pie.push(/*[data.windowsEnd,*/ data.itemTypeAndSumPriceArrayList[i].itemSumNum)
        //   this.option.series.push({
        //     name: data.itemTypeAndSumPriceArrayList[i].itemType,
        //     type: 'line',
        //     // showSymbol: false,
        //     // hoverAnimation: false,
        //     data: pie,
        //   })
        // }
        // global.mod++
        // if(global.mod % 5 == 0) {
        //   // this.option.series.clear
        //   this.myecharts.clear()
        // }
        // let pie = []
        // typeData.push(data.itemTypeAndSumPriceArrayList[i].itemType)
        // timeSZ.push([data.windowsEnd])
        // pie.push([
        //   // name: data.itemTypeAndSumPriceArrayList[i].windowEnd,
        //   // data.itemTypeAndSumPriceArrayList[i].itemType,
        //   data.windowsEnd,
        //   data.itemTypeAndSumPriceArrayList[i].itemSumNum
        // ])
        // console.log("data.windowsEnd==" + data.windowsEnd)
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemSumNum ==" + data.itemTypeAndSumPriceArrayList[i].itemSumNum)
        // console.log("data.itemTypeAndSumPriceArrayList[i].itemType === " + data.itemTypeAndSumPriceArrayList[i].itemType)
        // this.option.series.push({
        //   name: data.itemTypeAndSumPriceArrayList[i].itemType,
        //   type: 'line',
        //   // showSymbol: false,
        //   // hoverAnimation: false,
        //   data: pie,
        // })
      }
      // this.option.xAxis.data=this.windowsEnd
      this.option.series[0].data = tmp['手机']
      this.option.series[1].data = tmp['日用品']
      this.option.series[2].data = tmp['电脑']
      this.option.series[3].data = tmp['平板']
      this.option.series[4].data = tmp['耳机']
      this.option.series[5].data = tmp['跑鞋']
      this.option.series[6].data = tmp['零食']
      this.option.series[7].data = tmp['电脑配件']
      // this.xAxis.data = typeData
      // let length = this.option.series.length
      // let a=[]
      // let b=[]
      // for(let i=length-5; i<length; i++){
      //   if(this.option.series[i] != null){
      //     a.push(this.option.series[i])
      //     // b.push(timeSZ[i])
      //     // b.push(this.option.series[i].data[0].windowsEnd)
      //     // console.log("timeSZ[i]" + timeSZ[i])
      //   }
      // }
      // console.log("a=" + a)
      // this.option.series=a
      // this.option.xAxis.data=b
      // this.option.series.pop()
      // let tmp = {'手机':[],'日用品':[],'电脑':[]}
      // for (let i=0; i<data.itemTypeAndSumPriceArrayList.length; i++){
      //     tmp[data.itemTypeAndSumPriceArrayList[i].itemType].push([data.windowsEnd, data.itemTypeAndSumPriceArrayList[i].itemSumNum])
      // }
      // this.option.series[0].data = tmp['手机']
      // this.option.series[1].data = tmp['日用品']
      // this.option.series[2].data = tmp['电脑']
      this.myecharts.setOption(this.option)
      this.spinShow = false
    })
  },
}
</script>

<style scoped>
.spin-icon-load{
  animation: ani-demo-spin 1s linear infinite;
}
</style>