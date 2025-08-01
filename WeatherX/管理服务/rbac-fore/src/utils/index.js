import Cookies from 'js-cookie'
import store from '@/store'
import Constants from '@/utils/Constants'
import { v4 as uuidv4 } from 'uuid';

/**
 * 权限
 * @param {*} key
 */
export function hasPermission(key) {
    return window.SITE_CONFIG['permissions'].indexOf(key) !== -1 || false
}

/**
 * 清除登录信息
 */
export function clearLoginInfo() {
    store.commit('resetStore')
    Cookies.remove(Constants.token)
    window.SITE_CONFIG['dynamicMenuRoutesHasAdded'] = false
}

/**
 * 获取uuid
 */
export function getUUID() {
    return uuidv4();
}

/**
 * 获取svg图标(id)列表
 */
export function getIconList() {
    var res = []
    var list = document.querySelectorAll('svg symbol')
    for (var i = 0; i < list.length; i++) {
        res.push(list[i].id)
    }
    return res
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate(data, id = 'id', pid = 'pid') {
    var res = []
    var temp = {}
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (!temp[data[k][pid]] || data[k][id] === data[k][pid]) {
            res.push(data[k])
            continue
        }
        if (!temp[data[k][pid]]['children']) {
            temp[data[k][pid]]['children'] = []
        }
        temp[data[k][pid]]['children'].push(data[k])
        data[k]['_level'] = (temp[data[k][pid]]._level || 0) + 1
    }
    return res
}