<template>
    <div>
        <!-- {{ $store.state.username }}
    {{ $store.state.userPhone }}
    {{ $store.state.userpicture }}
    <img :src="$store.state.userpicture" alt=""> -->
        <el-container style="height: 100vh;">
            <el-header>
                <HeaderVue/>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <AsideVue/>
                </el-aside>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import AsideVue from '@/components/ShowAll/AsideVue.vue';
import HeaderVue from '@/components/ShowAll/HeaderVue.vue';

export default {
    components:{
        AsideVue,
        HeaderVue
    }
}
</script>

<style>

</style>