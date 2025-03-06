import 'dart:io';

import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart';
import 'package:shelf_router/shelf_router.dart';

import 'package:client_service/api/user_api.dart';
import 'package:client_service/repositories/user_repository.dart';
import 'package:client_service/services/user_service.dart';

void main(List<String> args) async {
  // Create dependencies
  final userRepository = UserRepository();
  final userService = UserService(userRepository);
  final userApi = UserApi(userService);
  
  // Create a router and add the API routes
  final router = Router();
  userApi.registerRoutes(router);

  // Add CORS middleware
  Handler handler = const Pipeline()
      .addMiddleware(logRequests())
      .addMiddleware(_corsHeaders())
      .addHandler(router.call);

  // Start the server
  final port = int.parse(Platform.environment['PORT'] ?? '8080');
  final server = await serve(handler, InternetAddress.anyIPv4, port);
  print('Server running on port ${server.port}');
}

Middleware _corsHeaders() {
  return (Handler handler) {
    return (Request request) async {
      final response = await handler(request);
      return response.change(headers: {
        ...response.headers,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE',
        'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
      });
    };
  };
}