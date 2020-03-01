const auth = {
  namespaced: true,
  state: {
    token: false,
    staffId: 0,
    loginId: '',
    role: '',
    expire: 0,
  },
  mutations: {
    SET_LOGIN_INFO: (state, auth) => {
      state.token   = auth.token   // JWT
      state.staffId = auth.staffId // 社員ID
      state.loginId = auth.loginId // ログインID
      state.role    = auth.role    // 権限
      state.expire  = Math.floor(auth.expire)  // JWT有効期限
    }
  },
  actions: {
    setLoginInfo({ commit }, auth) {
      commit("SET_LOGIN_INFO", auth)
    }
  }
}

export default auth