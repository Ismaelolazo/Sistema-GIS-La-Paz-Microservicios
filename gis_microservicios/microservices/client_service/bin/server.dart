import 'dart:io';
import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as io;
import 'package:shelf_router/shelf_router.dart';
import 'dart:convert';

void main() async {
  final app = Router();

  // Health check endpoint
  app.get('/health', (Request request) {
    return Response.ok('OK');
  });

  // User endpoints
  app.post('/api/users', (Request request) async {
    final payload = await request.readAsString();
    final user = jsonDecode(payload);
    return Response.ok(
      jsonEncode({
        'success': true,
        'data': {...user, 'id': 'generated-id'}
      }),
      headers: {'content-type': 'application/json'},
    );
  });

  final handler = Pipeline()
      .addMiddleware(logRequests())
      .addMiddleware(corsHeaders())
      .addHandler(app);

  final port = int.parse(Platform.environment['PORT'] ?? '8080');
  final server = await io.serve(handler, '0.0.0.0', port);
  print('Server listening on port ${server.port}');
}

Middleware corsHeaders() {
  return createMiddleware(
    requestHandler: (Request request) => null,
    responseHandler: (Response response) {
      return response.change(headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        'Access-Control-Allow-Headers': 'Origin, Content-Type',
      });
    },
  );
}
