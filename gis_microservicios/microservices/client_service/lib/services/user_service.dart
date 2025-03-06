import '../models/user.dart';
import '../repositories/user_repository.dart';

class UserService {
  final UserRepository _repository;

  UserService(this._repository);

  Future<List<User>> getAllUsers() async {
    return await _repository.getAll();
  }

  Future<User?> getUserById(int id) async {
    return await _repository.getById(id);
  }

  Future<User> createUser(User user) async {
    return await _repository.create(user);
  }

  Future<User?> updateUser(int id, User user) async {
    return await _repository.update(id, user);
  }

  Future<bool> deleteUser(int id) async {
    return await _repository.delete(id);
  }
}