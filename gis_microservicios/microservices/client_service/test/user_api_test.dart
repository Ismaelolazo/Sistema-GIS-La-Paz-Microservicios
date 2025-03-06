import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:test/test.dart';

void main() {
  const baseUrl = 'http://localhost:8080';
  Map<String, dynamic>? createdUser;

  group('User API tests', () {
    test('Health check', () async {
      final response = await http.get(Uri.parse('$baseUrl/health'));
      expect(response.statusCode, 200);
    });

    test('Create user', () async {
      final user = {
        'nombre': 'Test User',
        'correo': 'test@example.com',
        'contra': 'password123'
      };

      final response = await http.post(
        Uri.parse('$baseUrl/api/users'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(user),
      );

      expect(response.statusCode, 200);
      
      final responseBody = jsonDecode(response.body);
      expect(responseBody['success'], true);
      expect(responseBody['data']['nombre'], 'Test User');
      expect(responseBody['data']['id'], isNotNull);

      createdUser = responseBody['data'];
    });

    test('Get all users', () async {
      final response = await http.get(Uri.parse('$baseUrl/api/users'));
      expect(response.statusCode, 200);
      
      final responseBody = jsonDecode(response.body);
      expect(responseBody['success'], true);
      expect(responseBody['data'], isA<List>());
    });

    test('Get user by ID', () async {
      if (createdUser == null) {
        fail('Created user is null, cannot continue test');
      }

      final response = await http.get(
        Uri.parse('$baseUrl/api/users/${createdUser!['id']}'),
      );

      expect(response.statusCode, 200);
      
      final responseBody = jsonDecode(response.body);
      expect(responseBody['success'], true);
      expect(responseBody['data']['id'], createdUser!['id']);
    });

    test('Update user', () async {
      if (createdUser == null) {
        fail('Created user is null, cannot continue test');
      }

      final updatedData = {
        ...createdUser!,
        'nombre': 'Updated Name',
        'correo': 'updated@example.com',
      };

      final response = await http.put(
        Uri.parse('$baseUrl/api/users/${createdUser!['id']}'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(updatedData),
      );

      expect(response.statusCode, 200);
      
      final responseBody = jsonDecode(response.body);
      expect(responseBody['success'], true);
      expect(responseBody['data']['nombre'], 'Updated Name');
      expect(responseBody['data']['correo'], 'updated@example.com');
    });

    test('Delete user', () async {
      if (createdUser == null) {
        fail('Created user is null, cannot continue test');
      }

      final response = await http.delete(
        Uri.parse('$baseUrl/api/users/${createdUser!['id']}'),
      );

      expect(response.statusCode, 200);
      
      final responseBody = jsonDecode(response.body);
      expect(responseBody['success'], true);
      expect(responseBody['message'], 'User deleted successfully');
    });
  });
}