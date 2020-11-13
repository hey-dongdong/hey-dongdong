const MessageType = {
    MESSAGE: 'message'
};

export default class WebSocketAdmin {
    connect() {
        this.socket = new WebSocket('ws://localhost:8081');
        this.onOpen = this.onOpen.bind(this);
        this.onMessage = this.onMessage.bind(this);
        this.socket.addEventListener('open', this.onOpen);
        this.socket.addEventListener('message', this.onMessage);
        this._callbacks = {};
    }
    onOpen () {
        this.doCallback('open');
    }
    on (event, callback) {
        this._callbacks[event] = this._callbacks[event] || [];
        this._callbacks[event].push(callback);
    }
    isOpen () {
        return this.socket.readyState === this.socket.OPEN
    }
    doCallback (event, data) {
        const callbacks = this._callbacks[event];
        if (callbacks) {
            callbacks.map(cb => cb(data));
        }
    }
    status () {
        switch (this.socket.readyState) {
            case this.socket.OPEN:
                return 'open';
            case this.socket.CLOSED:
                return 'closed';
            case this.socket.CLOSING:
                return 'closing';
        }
    }
    onMessage (event) {
        try {
            const parsedMessage = JSON.parse(event.data);
            console.info(event.data);
            console.info(parsedMessage);
            console.info(parsedMessage.type);
            console.info(parsedMessage.payload);
            switch(parsedMessage.type) {
                case MessageType.MESSAGE:
                    console.log(`Message from customer: ${parsedMessage.payload}`);
                    break;
                default:
                    console.log(`Message from customer: ${parsedMessage.payload}`);
            }
        } catch (e) {
            console.error(e);
        }
    }

    sendData (command, payload) {
        const data = {
            command,
            data: btoa(payload)
        };

        if(!this.isOpen()) return;
        this.socket.send(JSON.stringify(data));
    }
}
