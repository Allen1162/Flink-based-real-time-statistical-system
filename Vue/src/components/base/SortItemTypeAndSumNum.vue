<template>
  <div style="margin-left: 32px">
    <div id="flip-list-demo" class="demo">
      <Spin fix v-if="spinShow">
        <Icon type="ios-loading" size=18 class="spin-icon-load"/>
        <div>Loading</div>
      </Spin>
      <h1 style="display: flex;justify-content: center">商品销量前三排行榜</h1>
      <transition-group name="flip-list" >
        <Row class-name="rowmargin" type="flex" :gutter="16" v-for=" item in sortItemSumNum" v-bind:key="item.itemType">
          <Col span="7">
            <h5 style="display: flex;justify-content: flex-end">{{item.itemType}}</h5>
          </Col>
          <Col span="14" >
            <Progress
                :percent="item.percent"
                :stroke-color="['#108ee9', '#87d068']"
                :hide-info="true"
                :stroke-width="20" >
            </Progress>
          </Col>
          <Col span="1">
            <animated-number
                :value="item.itemSumNum"
                :formatValue="formatter"
                :duration="1000"
            />
          </Col>

        </Row>

      </transition-group>
    </div>

  </div>
</template>

<script>
import RootVue from '../../root/RootVue';
import AnimatedNumber from "animated-number-vue"
export default {
  components: {
    AnimatedNumber,
  },
  data(){
    return{
      spinShow:true,
      sortItemSumNum: [],
      myData:{
        sortItemSumNum:[{
          itemType:"",
          itemSumNum:0,
          itemSumPrice:0,
          windowEnd: 0
        }]
      }
    }
  },
  mounted() {
    RootVue.$on("ITEM:TYPE:SUMNUM:SUMPRICE:SORTSUMNUM", message => {
      let data = JSON.parse(message)
      // console.log("USER:HOME:OUTCOME:SORT 的data : " + data)
      this.sortItemSumNum = data.sortItemSumNum
      // console.log("this.sortUserHomeAndOutcomes ==" + this.sortUserHomeAndOutcomes)
      let max = 0
      for(let i=0; i<this.sortItemSumNum.length; i++){
        max += this.sortItemSumNum[i].itemSumNum
      }
      // console.log("max ==" + max)
      for (let i = 0; i < this.sortItemSumNum.length; i++){
        this.sortItemSumNum[i].percent = (this.sortItemSumNum[i].itemSumNum * 100 / max)
        // console.log("this.sortUserHomeAndOutcomes[i].percent " + "i=" + i + "  " + this.sortUserHomeAndOutcomes[i].percent)
      }
      // this.items = array
      this.spinShow=false
    })
  },
  methods: {
    formatter(value) {
      return Math.floor(value);
    }
  }

}
</script>

<style scoped>
.flip-list-move {
  transition: transform 1s;
}
.rowmargin {
  margin-top: 8px;
  margin-bottom: 8px;
}
.spin-icon-load{
  animation: ani-demo-spin 1s linear infinite;
}
</style>