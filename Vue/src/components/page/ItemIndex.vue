<template>
  <div>
    <Row>
      <Col span="12">
        <item-type-and-sum-num/>
      </Col>
      <Col span="12">
        <item-type-and-sum-price/>
      </Col>
    </Row>
    <Row type="flex" :gutter="16">
      <Col span="24">
        <sort-item-type-and-sum-num/>
      </Col>
      <Col span="24">
        <sort-item-type-and-sum-price/>
      </Col>
    </Row>
  </div>
</template>

<script>
import ItemTypeAndSumNum from "@/components/base/ItemTypeAndSumNum";
import ItemTypeAndSumPrice from "@/components/base/ItemTypeAndSumPrice";
import SortItemTypeAndSumNum from "@/components/base/SortItemTypeAndSumNum";
import SortItemTypeAndSumPrice from "@/components/base/SortItemTypeAndSumPrice";
export  default {
  components:{
    ItemTypeAndSumPrice,
    ItemTypeAndSumNum,
    SortItemTypeAndSumNum,
    SortItemTypeAndSumPrice
  }
}
</script>

<style scoped>

</style>
