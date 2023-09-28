import createLogger from 'vuex/dist/logger'


const debug = process.env.NODE_ENV !== "production";

export const plugins = debug?[createLogger()]:[];
export const state = () => ({
    counter: 0
  })
  
  export const mutations = {
    increment(state) {
      state.counter++
    }
  }

  export const actions = {
    async nuxtServerInit({ dispatch }) {
      // logger.log("dispatch", dispatch);
      await dispatch('siteInfo/getSiteInfo'); 
      //  await   dispatch("siteInfo/getSiteIndexLink"); 
      //  await  dispatch('siteInfo/getSiteGo');
    }
  }

  const logger = createLogger({
    collapsed: false, // 自动展开记录的 mutation
    filter (mutation, stateBefore, stateAfter) {
      // 若 mutation 需要被记录，就让它返回 true 即可
      // 顺便，`mutation` 是个 { type, payload } 对象
      return mutation.type !== "aBlocklistedMutation"
    },
    actionFilter (action, state) {
      // 和 `filter` 一样，但是是针对 action 的
      // `action` 的格式是 `{ type, payload }`
      return action.type !== "aBlocklistedAction"
    },
    transformer (state) {
      // 在开始记录之前转换状态
      // 例如，只返回指定的子树
      return state.subTree
    },
    mutationTransformer (mutation) {
      // mutation 按照 { type, payload } 格式记录
      // 我们可以按任意方式格式化
      return mutation.type
    },
    actionTransformer (action) {
      // 和 `mutationTransformer` 一样，但是是针对 action 的
      return action.type
    },
    logActions: true, // 记录 action 日志
    logMutations: true, // 记录 mutation 日志
    logger: console, // 自定义 console 实现，默认为 `console`
  })