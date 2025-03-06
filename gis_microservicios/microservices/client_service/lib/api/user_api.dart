import 'dart:convert';
import 'package:shelf/shelf.dart';
import 'package:shelf_router/shelf_router.dart';

import '../models/user.dart';
import '../services/user_service.dart';

class UserApi {
  final UserService _service;

  UserApi(this._service);

  void registerRoutes(Router router) {
    // Get all users
    router.get('/api/users', _getAllUsers);

    // Get user by ID
    router.get('/api/users/<id>', _getUserById);

    // Create user
    router.post('/api/users', _createUser);

    // Update user
    router.put('/api/users/<id>', _updateUser);

    // Delete user
    router.delete('/api/users/<id>', _deleteUser);

    // Health check
    router.get('/health', (Request request) {
      return Response.ok('User service is running');
    });
  }

  Future<Response> _getAllUsers(Request request) async {
    try {
      final users = await _service.getAllUsers();
      return Response.ok(
        jsonEncode({
          'success': true,
          'data': users.map((u) => u.toJson()).toList(),
        }),
        headers: {'Content-Type': 'application/json'},
      );
    } catch (e) {
      return Response.internalServerError(
        body: jsonEncode({
          'success': false,
          'message': 'Failed to retrieve users: $e',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    }
  }

  Future<Response> _getUserById(Request request, String id) async {
    try {
      final userId = int.tryParse(id);
      if (userId == null) {
        return Response.badRequest(
          body: jsonEncode({
            'success': false,
            'message': 'Invalid user ID format',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      final user = await _service.getUserById(userId);
      if (user == null) {
        return Response.notFound(
          jsonEncode({
            'success': false,
            'message': 'User not found',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      return Response.ok(
        jsonEncode({
          'success': true,
          'data': user.toJson(),
        }),
        headers: {'Content-Type': 'application/json'},
      );
    } catch (e) {
      return Response.internalServerError(
        body: jsonEncode({
          'success': false,
          'message': 'Failed to retrieve user: $e',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    }
  }

  Future<Response> _createUser(Request request) async {
    try {
      final bodyBytes = await request.read().fold<List<int>>(
        <int>[],
        (previous, element) => previous..addAll(element),
      );
      final bodyContent = utf8.decode(bodyBytes);
      print('Received body: $bodyContent'); // Debug print

      if (bodyContent.isEmpty) {
        return Response.badRequest(
          body: jsonEncode({
            'success': false,
            'message': 'Request body is empty',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      final bodyJson = jsonDecode(bodyContent) as Map<String, dynamic>;

      final user = User.fromJson(bodyJson);
      final createdUser = await _service.createUser(user);

      return Response.ok(
        jsonEncode({
          'success': true,
          'data': createdUser.toJson(),
        }),
        headers: {'Content-Type': 'application/json'},
      );
    } catch (e) {
      return Response.internalServerError(
        body: jsonEncode({
          'success': false,
          'message': 'Failed to create user: $e',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    }
  }

  Future<Response> _updateUser(Request request, String id) async {
    try {
      final userId = int.tryParse(id);
      if (userId == null) {
        return Response.badRequest(
          body: jsonEncode({
            'success': false,
            'message': 'Invalid user ID format',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      final bodyBytes = await request.read().fold<List<int>>(
        <int>[],
        (previous, element) => previous..addAll(element),
      );
      final bodyJson =
          jsonDecode(utf8.decode(bodyBytes)) as Map<String, dynamic>;

      final user = User.fromJson(bodyJson);
      final updatedUser = await _service.updateUser(userId, user);

      if (updatedUser == null) {
        return Response.notFound(
          jsonEncode({
            'success': false,
            'message': 'User not found',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      return Response.ok(
        jsonEncode({
          'success': true,
          'data': updatedUser.toJson(),
        }),
        headers: {'Content-Type': 'application/json'},
      );
    } catch (e) {
      return Response.internalServerError(
        body: jsonEncode({
          'success': false,
          'message': 'Failed to update user: $e',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    }
  }

  Future<Response> _deleteUser(Request request, String id) async {
    try {
      final userId = int.tryParse(id);
      if (userId == null) {
        return Response.badRequest(
          body: jsonEncode({
            'success': false,
            'message': 'Invalid user ID format',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      final deleted = await _service.deleteUser(userId);

      if (!deleted) {
        return Response.notFound(
          jsonEncode({
            'success': false,
            'message': 'User not found',
          }),
          headers: {'Content-Type': 'application/json'},
        );
      }

      return Response.ok(
        jsonEncode({
          'success': true,
          'message': 'User deleted successfully',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    } catch (e) {
      return Response.internalServerError(
        body: jsonEncode({
          'success': false,
          'message': 'Failed to delete user: $e',
        }),
        headers: {'Content-Type': 'application/json'},
      );
    }
  }
}
