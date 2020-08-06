import { SET_PATH } from '@/store/shared/mutationTypes'
import { SEARCH_PATH } from '@/store/shared/actionTypes'
import PathService from '@/api/modules/path'

const state = {
  pathResult: null
}

const getters = {
  pathResult(state) {
    return state.pathResult
  }
}

const mutations = {
  setPath(state, pathResult) {
    state.pathResult = pathResult
  }
}

const actions = {
  async searchPath({ commit }, {source, target, type}) {
    return PathService.get({source, target, type}).then(({ data }) => {
      commit('setPath', data)
    })
  },
  async searchFastestPath({ commit }, {source, target, currentTime}) {
    return PathService.getFastest({source, target, currentTime}).then(({ data }) => {
      commit('setPath', data);
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
