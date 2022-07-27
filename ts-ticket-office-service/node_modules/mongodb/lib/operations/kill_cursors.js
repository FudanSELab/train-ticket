"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.KillCursorsOperation = void 0;
const error_1 = require("../error");
const operation_1 = require("./operation");
class KillCursorsOperation extends operation_1.AbstractOperation {
    constructor(cursorId, ns, server, options) {
        super(options);
        this.ns = ns;
        this.cursorId = cursorId;
        this.server = server;
    }
    execute(server, session, callback) {
        if (server !== this.server) {
            return callback(new error_1.MongoRuntimeError('Killcursor must run on the same server operation began on'));
        }
        server.killCursors(this.ns, [this.cursorId], { session }, () => callback());
    }
}
exports.KillCursorsOperation = KillCursorsOperation;
(0, operation_1.defineAspects)(KillCursorsOperation, [operation_1.Aspect.MUST_SELECT_SAME_SERVER]);
//# sourceMappingURL=kill_cursors.js.map