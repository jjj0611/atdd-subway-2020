import ApiService from '@/api'



const PathService = {
  get({source, target, type}) {
    return ApiService.get(`/get?source=${source}&target=${target}&type=${type}`)
  },
  getFastest({source, target, currentTime}) {
    return ApiService.get(`/get?source=${source}&target=${target}&currentTime=${currentTime}`)
  }
}

export default PathService
