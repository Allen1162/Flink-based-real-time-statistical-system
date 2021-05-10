<template>
  <div>
    <Row>
      <Col span="24">
        <user-home-and-outcome/>
      </Col>
    </Row>

    <Row type="flex" :gutter="16">
      <Col span="24">
        <sort-user-home-and-outcome/>
      </Col>
    </Row>
  </div>
</template>

<script>
// import OnlineNum from "../base/OnlineNum";
// import LineBox from "../base/LineBox"
// import MapBox from "../base/MapBox"
// import RangeBarBox from "../base/RangeBarBox";
// import User from "../base/User";
import UserHomeAndOutcome from "@/components/base/UserHomeAndOutcome";
import SortUserHomeAndOutcome from "@/components/base/SortUserHomeAndOutcome";
export  default {
  components:{
    // OnlineNum,
    // LineBox,
    // MapBox,
    // RangeBarBox
    // User,
    UserHomeAndOutcome,
    SortUserHomeAndOutcome
  }
}
</script>

<style scoped>

</style>
