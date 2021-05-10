<template>
  <div>
    <Spin fix v-if="spinShow">
      <Icon type="ios-loading" size=18 class="spin-icon-load"/>
      <div>Loading</div>
    </Spin>
    <h1 class="usercenter">用户来源分析</h1>
    <card class="usercenter">
      <!-- 初始化echarts 需要有个带有宽高的盒子   -->
      <div ref="pipebox"  style="height: 300px;width: 700px"></div>
      <div class="usercenter">
<!--        总用户数：-->
<!--        {{allUserNum}}人-->
      </div>
    </card>
    <h1 class="usercenter">活跃用户分析</h1>
    <Row :gutter="16">
      <Col span="8" v-for="(item,index) in userSourceAndNums" :key="index">
        <card class="usercenter">
          <i-circle
              dashboard
              :size="250"
              :trail-width="4"
              :stroke-width="5"
              :percent="item.userNum"
              stroke-linecap="square"
              stroke-color="#43a3fb">
<!--            <div class="demo-Circle-custom">-->
<!--              <h1>{{item.income}}</h1>-->
<!--              <p>{{item.userHome}}用户规模</p>-->
<!--              <span>-->
<!--                            活跃用户占比<br/>-->
<!--                            <i><animated-number-->
<!--                                :value="item.income"-->
<!--                                :formatValue="doubleFormatter"-->
<!--                                :duration="1000"-->
<!--                            /></i>-->
<!--              </span>-->
<!--            </div>-->
          </i-circle>
        </card>
      </Col>
    </Row>
  </div>
</template>

<script>
import RootVue from '../../root/RootVue';
import echarts from 'echarts'
// import AnimatedNumber from "animated-number-vue"
export default {
  components: {

  },
  data(){
    return{
      spinShow:true,
      // allUserNum: 0,
      userSourceAndNums:[],
      mydata:{
        userSourceAndNums:[{
          userSource:"",
          userNum:0,
          windowEnd:"",
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
          left: '10%',
          data: ['Web', 'Phone']
        },
        tooltip: {
          trigger: 'item',
        },
        series: [{
          name: "用户来源",
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
    this.myecharts = echarts.init(this.$refs.pipebox)
    RootVue.$on("USER:SOURCE:NUM", message =>{
      let data = JSON.parse(message)
      // this.allUserNum = data.allUserNum
      this.userSourceAndNums = data.userSourceAndNums
      let pie = []
      for (let i in data.userSourceAndNums){
        pie.push({
          value: data.userSourceAndNums[i].userSource,
          name: data.userSourceAndNums[i].userNum
        })
      }
      this.option.series[0].data = pie
      this.myecharts.setOption(this.option)
      this.spinShow = false
    })
  },
  methods:{
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
.usercenter{
  display: flex;
  justify-content: center;
  margin-top: 16px;
  margin-bottom: 16px;
}
.demo-Circle-custom h1{
  color: #3f414d;
  font-size: 28px;
  font-weight: normal;
}
.demo-Circle-custom p{
  color: #657180;
  font-size: 14px;
  margin: 10px 0 15px;
}
.demo-Circle-custom span{
  display: block;
  padding-top: 15px;
  color: #657180;
  font-size: 14px;

}
.demo-Circle-custom span:before{
  content: '';
  display: block;
  width: 50px;
  height: 1px;
  margin: 0 auto;
  background: #e0e3e6;
  position: relative;
  top: -15px;
}
.demo-Circle-custom span i{
  font-style: normal;
  color: #3f414d;
}
.spin-icon-load{
  animation: ani-demo-spin 1s linear infinite;
}
</style>
