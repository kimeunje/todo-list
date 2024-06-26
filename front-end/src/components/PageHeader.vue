<template>
  <div class="page-header d-flex align-content-center ">
    <div class="logo" @click="goHome()">
      <font-awesome-icon :icon="['fas', 'house']" class="home-icon" />
      <img src="/images/logo.png">
    </div>
    <div class="boards-menu-toggle">
      <div class="dropdown">
        <button class="btn dropdown-toggle" type="button" id="boardsMenu" data-bs-toggle="dropdown" aria-haspopup="true"
          aria-expanded="false">
          {{ t('header.boardsMenu.label') }}
        </button>
        <div class="dropdown-menu" aria-labelledby="boardsMenu">
          <div v-show="!hasBoards" class="dropdown-item">{{ t('header.boardsMenu.noBoard') }}</div>
          <div v-show="hasBoards">
            <h6 class="dropdown-header" v-show="personalBoards.length">{{ t('header.boardsMenu.personalBoards') }}</h6>
            <button v-for="board in personalBoards" v-bind:key="board.id" @click="openBoard(board)"
              class="dropdown-item" type="button">{{ board.name }}</button>
            <div v-for="team in teamBoards" v-bind:key="'t' + team.id">
              <h6 class="dropdown-header">{{ team.name }}</h6>
              <button v-for="board in team.boards" v-bind:key="board.id" @click="openBoard(board)" class="dropdown-item"
                type="button">{{ board.name }}</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- <div class="search-box flex-fill">
      <div class="search-wrapper">
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="search-icon" />
        <input type="text" v-bind:placeholder="t('header.search')" class="form-control form-control-sm" />
      </div>
    </div> -->

    <div class="profile-menu-toggle flex-fill">
      <div class="dropdown">
        <button class="btn dropdown-toggle" type="button" id="profileMenu" data-bs-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">
          {{ getUser.name }}
        </button>
        <div class="dropdown-menu" aria-labelledby="profileMenu">
          <button class="dropdown-item" type="button">{{ t('header.profile') }}</button>
          <button class="dropdown-item" type="button" @click="signOut()">{{ t('header.signOut') }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { useI18n } from 'vue-i18n';
import meService from '@/services/me'
import notify from '@/utils/notify'

const { getUser, hasBoards, personalBoards, teamBoards } = storeToRefs(useBoardUserStore())
const { getMyData } = useBoardUserStore()
const router = useRouter()

const { t } = useI18n();

onMounted(() => {
  getMyData()
});

const goHome = () => {
  router.push({ name: 'home' })
}

const openBoard = (board: { id: number }) => {
  router.push({ name: 'board', params: { boardId: board.id } })
}

const signOut = () => {
  meService.signOut().then(() => {
    router.push({ name: 'login' })
  }).catch(error => {
    notify.error(error.message)
  })

}

</script>

<style lang="scss" scoped>
.page-header {
  padding: 9px 10px 8px;
  border-bottom: 1px solid #eee;

  .logo {
    color: #444;
    height: 25px;
    width: 115px;
    margin-top: 2px;
    cursor: pointer;
    white-space: nowrap;

    .home-icon {
      font-size: 20px;
      vertical-align: middle;
    }

    img {
      margin-left: 3px;
      width: 80px;
      height: 20px;
    }
  }

  .boards-menu-toggle {
    padding-left: 20px;
    width: 100px;
  }

  .profile-menu-toggle {
    width: 215px;
    text-align: right;
  }

  .search-box {
    .search-wrapper {
      width: 400px;
      margin: 0 auto;
      position: relative;
    }

    .search-icon {
      position: absolute;
      top: 8px;
      left: 8px;
      color: #666;
    }

    input {
      padding-left: 30px;
      height: calc(1.8125rem + 5px);
      font-size: 1rem;
      border: 1px solid #eee;
      border-radius: 5px;
    }
  }
}

.dropdown-toggle {
  padding: 2px 5px !important;
}
</style>
