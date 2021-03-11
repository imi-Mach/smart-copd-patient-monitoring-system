import https from './http-common';

class PostsDataService {
  getAll() {
    return https.get('/posts');
  }

  getReviews() {
    return https.get('/reviews');
  }

  getCourseReviews(id: string) {
    return https.get(`/reviews/${id}`);
  }

  getUserReviews(id: string) {
    return https.get(`/reviews/user/${id}`);
  }

  deleteReview(id: number) {
    return https.delete(`/reviews/${id}`);
  }

  get(id: number) {
    return https.get(`/posts/${id}`);
  }

  create(data: any) {
    return https.post('/posts', data);
  }

  createReview(data: any) {
    return https.post('/reviews', data);
  }

  createReport(data: any) {
    return https.post('/reports', data);
  }

  update(id: string, data: any) {
    return https.put(`/posts/${id}`, data);
  }

  delete(id: number) {
    return https.delete(`/posts/${id}`);
  }
}

export default new PostsDataService();
