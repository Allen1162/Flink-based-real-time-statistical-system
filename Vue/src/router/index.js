import UserIndex from "../components/page/UserIndex";
import ItemIndex from "@/components/page/ItemIndex";
import TestItemIndex from "@/components/page/TestItemIndex"
// import OtherIndex from "../components/page/OtherIndex";
// import UserIndex from "../components/page/UserIndex";
export default  [
    {
        path:'/',
        redirect:'/UserIndex'
    },
    {
        path:'/UserIndex',
        component:UserIndex
    },
    {
        path: '/ItemIndex',
        component: ItemIndex
    },
    {
        path: '/TestItemIndex',
        component: TestItemIndex
    },
    {
        path:'*',
        redirect:'/UserIndex'},
]