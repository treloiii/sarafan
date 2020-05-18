<template>
    <router-link
        :to="`/user/${author.id}`"
    >
        <v-list-item two-line>
            <v-list-item-avatar color="indigo" v-if="author&&author.userpic" :size="size">
                <v-img :src="author.userpic"/>
            </v-list-item-avatar>
            <v-list-item-avatar color="indigo" v-else :size="size">
                <v-img src="https://forum.develop-soft.ru/images/avatars/random/default-avatar.svg"/>
            </v-list-item-avatar>
        </v-list-item>
    </router-link>
</template>

<script>
    export default {
        name: "UserLink",
        props:["author","size"],
        computed:{
        }
    }
</script>

<style scoped>

</style>