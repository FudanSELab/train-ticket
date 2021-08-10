/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package inside_payment.service;

import edu.fudan.common.util.Response;
import inside_payment.entity.*;
import org.springframework.http.HttpHeaders;


/**
 * @author Administrator
 * @date 2017/6/20.
 */
public interface InsidePaymentService {

    /**
     * pay by payment info
     *
     * @param info payment info
     * @param headers headers
     * @return Response
     */
    Response pay(PaymentInfo info , HttpHeaders headers);

    /**
     * create account by payment info
     *
     * @param info payment info
     * @param headers headers
     * @return Response
     */
    Response createAccount(AccountInfo info, HttpHeaders headers);

    /**
     * add money with user id, money
     *
     * @param userId user id
     * @param  money money
     * @param headers headers
     * @return Response
     */
    Response addMoney(String userId,String money, HttpHeaders headers);

    /**
     * query payment info
     *
     * @param headers headers
     * @return Response
     */
    Response queryPayment(HttpHeaders headers);

    /**
     * query account info
     *
     * @param headers headers
     * @return Response
     */
    Response queryAccount(HttpHeaders headers);

    /**
     * drawback with user id, money
     *
     * @param userId user id
     * @param  money money
     * @param headers headers
     * @return Response
     */
    Response drawBack(String userId, String money, HttpHeaders headers);

    /**
     * pay difference by payment info
     *
     * @param info payment info
     * @param headers headers
     * @return Response
     */
    Response payDifference(PaymentInfo info, HttpHeaders headers);

    /**
     * query add money
     *
     * @param headers headers
     * @return Response
     */
    Response queryAddMoney(HttpHeaders headers);

    /**
     * init payment
     *
     * @param payment payment
     * @param headers headers
     * @return Response
     */
    void initPayment(Payment payment, HttpHeaders headers);

}
