from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from atm.ATM import Client
from enum import Enum

# Define api codes
class Code(Enum):
    WITHDRAW = 0
    DEPOSIT  = 1
    BALANCE  = 2

# Define status codes
class StatusCode(Enum):
    OK                   = 200
    BAD_REQUEST          = 400
    INSUFFICIENT_BALANCE = 8000
    NEGATIVE_AMOUNT      = 8001
    USER_NOT_FOUND       = 8002

def clear_terminal():
    for i in range(0, 10):
        print()

def menu():
    while True:
        print('+--------------------+')
        print('|(0)----Withdraw-----|')
        print('|(1)----Deposit------|')
        print('|(2)----Balance------|')
        print('+--------------------+')
        choice = input('Please select an option[0-2]: ')
        try:
            code = int(choice)
            if (code >= 0 and code <= 2):
                return code
        except ValueError:
            print('Enter a valid value...')

def get_result(client, code, user_id, amount):
    match code:
        case Code.WITHDRAW.value:
            return client.withdraw(user_id, amount)
        case Code.DEPOSIT.value:
            return client.deposit(user_id, amount)
        case Code.BALANCE.value:
            return client.get_balance(user_id)
        case _:
            return -1

def process_result(result):
    match result:
        case StatusCode.OK.value:
            return 'Successful!'
        case StatusCode.BAD_REQUEST.value:
            return 'Request body is invalid...'
        case StatusCode.USER_NOT_FOUND.value:
            return 'User was not found...'
        case StatusCode.INSUFFICIENT_BALANCE.value:
            return 'Insufficient balance...'
        case _:
            return 'Unrecognized status code'


if __name__ == '__main__':
    transport = TSocket.TSocket('server', 9090)

    transport = TTransport.TBufferedTransport(transport)

    # Wrap in a protocol
    protocol = TBinaryProtocol.TBinaryProtocol(transport)

    # Create a client to use the protocol encoder
    client = Client(protocol)

    # Connect
    transport.open()

    # Define constants for user
    user_id = 1

    # Show menu in a while loop and get code
    while True:
        code = menu()
        # Get input for amount
        amount = -1.0
        if code != Code.BALANCE.value:
            while True:
                amount_str = input('Please enter an amount: ')
                try:
                    amount = float(amount_str)
                    if amount > 0:
                        break
                except ValueError:
                    print('Please enter a valid value...')

        # Call appropriate function based on code provided
        result = get_result(client, code, user_id, amount)

        # Produce message
        message = process_result(result)
        if code == Code.BALANCE.value:
            message = f'Balance is {result}'
        print(f'Status: {message}')

        # Exit message
        answer = input('Enter c to continue, otherwise enter any other key if you wish to exit...')
        if answer != 'c':
            break

        clear_terminal()
